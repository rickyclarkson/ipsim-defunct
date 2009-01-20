 package ipsim.network;
/*
 import fpeas.function.*;
 import fpeas.predicate.*;
 import fpeas.sideeffect.*;
 import static ipsim.Caster.*;
 import ipsim.*;
 import ipsim.gui.*;
 import ipsim.io.*;
 import ipsim.lang.*;
 import static ipsim.network.PacketSourceUtility.*;
 import ipsim.network.connectivity.*;
 import ipsim.network.connectivity.cable.*;
 import ipsim.network.connectivity.card.*;
 import ipsim.network.connectivity.computer.*;
 import ipsim.network.connectivity.hub.*;
 import ipsim.network.connectivity.ip.*;
 import static ipsim.network.ethernet.Computer.*;
 import ipsim.persistence.*;
 import static ipsim.persistence.delegates.NetworkDelegate.*;
 import ipsim.tree.*;
 import ipsim.util.Collections;
 import static ipsim.util.Collections.*;
 import ipsim.util.*;
 import ipsim.webinterface.*;
 import org.jetbrains.annotations.*;

 import java.io.*;
 import java.net.*;
 import java.util.*;

 public class NetworkUtility
 {
     public static Network loadFromString(final Network network, @NotNull final String xmlString)
         {
                 network.clearAll();

                 final XMLDeserialiser deserialiser;
                 try
                 {
                         deserialiser=new XMLDeserialiser(xmlString);
                 }
                 catch (IOException e)
                 {
                         UserMessages.message("There was a problem in loading the file: "+e.getMessage());
                         return network;
                 }

                 network.nextComputerID=200;

                 deserialiser.readObject(networkDelegate(network), asFunction(Network.class));

                 return network;
         }

         public static void saveToFile(final Network network, @NotNull final File filename) throws IOException
         {
                 if (filename.getName().startsWith("@"))
                 {
                         final StringWriter stringWriter=new StringWriter();

                         final boolean tempModified=network.modified;
                         saveToWriter(network, stringWriter);
                         network.modified=tempModified;

                         try
                         {
                                 final String output=WebInterface.putNamedConfiguration(filename.getName(), stringWriter.toString());
                                 if (!output.startsWith("102"))
                                         throw new RuntimeException(output);
                         }
                         catch (final IOException exception)
                         {
                                 throw new RuntimeException(exception);
                         }
                 }
                 else
                 {
                         final Writer bufferedWriter=new BufferedWriter(new FileWriter(filename));
                         try
                         {
                                 saveToWriter(network, bufferedWriter);
                         }
                         finally
                         {
                                 try
                                 {
                                         bufferedWriter.close();
                                 }
                                 catch (IOException exception)
                                 {
                                         exception.printStackTrace();
                                 }
                         }
                 }
         }

         public static void saveToWriter(final Network network, final Writer writer)
         {
                 saveObjectToWriter(writer, network, networkDelegate(network));
                 network.modified=false;
         }

         public static String saveToString(final Network network)
         {
                 final StringWriter writer=new StringWriter();
                 saveToWriter(network, writer);
                 return writer.toString();
         }

         public static Stream<PacketSource> getDepthFirstIterable(final Network network)
         {
                 Collection<PacketSource> set=Collections.hashSet();

                 for (final PacketSource p : Trees.getDepthFirstIterable(Trees.nodify(network, network.topLevelComponents())))
                         set.add(p);

                 return Stream.fromIterable(set);
         }

         //TODO make this not a copy of getDepthFirstIterable(network)
         public static Stream<PacketSource> getDepthFirstIterable(final Network network,final NetworkContext context)
         {
                 Collection<PacketSource> set=Collections.hashSet();

                 for (final PacketSource p: Trees.getDepthFirstIterable(Trees.nodify(network,context.topLevelComponents())))
                         set.add(p);

                 return Stream.fromIterable(set);
         }

         public static final Predicate<Random> testDepthFirstIterable=new Predicate<Random>()
         {
                 public boolean invoke(Random random)
                 {
                         Network network=Network.randomNetwork.run(random);
                         return getDepthFirstIterable(network).size()==network.getAll().size();
                 }
         };

         public static int getNumberOfSubnets(final Network network)
         {


                 final Collection<Integer> subnets=hashSet();

                 getAllComputers(network).foreach(new SideEffect<Computer>()
                 {
                         public void run(final Computer computer)
                         {
                                 cardsWithDrivers(network, computer).foreach(new SideEffect<CardDrivers>()
                         {
                                 public void run(final CardDrivers cardDrivers)
                                 {
                                     final int mask=cardDrivers.netMask.get().rawValue();
                                     final int ip=cardDrivers.ipAddress.get().rawValue();

                                         subnets.add(mask&ip);
                                 }
                         });
                         }
                 });

                 return subnets.size();
         }

         public static Stream<Computer> getComputersByIP(final Network network, final IPAddress ipAddress)
         {
                 return getDepthFirstIterable(network).only(PacketSourceUtility.isComputerRef).map(PacketSourceUtility.asComputerRef).only(new Predicate<Computer>()
                 {
                         public boolean invoke(final Computer computer)
                         {
                                 return !cardsWithDrivers(network, computer).only(new Predicate<CardDrivers>()
                                 {
                                         public boolean invoke(final CardDrivers cardDrivers)
                                         {
                                                 return cardDrivers.ipAddress.get().equals(ipAddress);
                                         }
                                 }).isEmpty();
                         }
                 });
         }

         public static final Function<Network, Stream<Hub>> getAllHubs=new Function<Network, Stream<Hub>>()
         {
                 @NotNull
                 public Stream<Hub> run(@NotNull final Network network)
                 {
                         return getAllHubs(network);
                 }
         };

         public static Stream<Hub> getAllHubs(final Network network)
         {
                 return getDepthFirstIterable(network).only(isHub).map(asHubRef);
         }

         public static void loadFromFile(final Network network, final File file)
         {
                 try
                 {
                         final String s=IOUtility.readWholeResource(file.toURI().toURL());
                         if (s==null)
                                 UserMessages.message("Could not load the requested file");
                         else
                                 loadFromString(network, s);
                 }
                 catch (final MalformedURLException exception)
                 {
                         throw new RuntimeException(exception);
                 }
         }

         public static final Function<Network, Stream<Computer>> getAllComputers=new Function<Network, Stream<Computer>>()
         {
                 @NotNull
                 public Stream<Computer> run(@NotNull final Network network)
                 {
                         return getAllComputers(network);
                 }
         };

         public static Stream<Computer> getAllComputers(final Network network, final Predicate<? super Computer> condition)
         {
                 return getDepthFirstIterable(network).only(PacketSourceUtility.isComputerRef).map(PacketSourceUtility.asComputerRef).only(condition);
         }

         public static final Function<Network, Stream<Card>> getAllCards=new Function<Network, Stream<Card>>()
         {
                 @NotNull
                 public Stream<Card> run(@NotNull final Network context)
                 {
                         return getAllCards(context);
                 }
         };

     public static Stream<Card> getAllCards(final Network network)
     {
         return getDepthFirstIterable(network).only(isCardRef).map(asCardRef);
     }

         public static Stream<CardDrivers> getAllCardsWithDrivers(final Network network)
         {
                 return getAllCards(network).only(Card.hasDrivers).map(Card.withDriversRef);
         }

         public static Function<Network, Stream<Cable>> getAllCables()
         {
                 return new Function<Network, Stream<Cable>>()
                 {
                         @NotNull
                         public Stream<Cable> run(@NotNull final Network context)
                         {
                                 return getAllCables(context);
                         }
                 };
         }

         public static Stream<Cable> getAllCables(final Network network)
         {
                 return getAllCables(network, Predicates.TRUE);
         }

         public static Stream<Cable> getAllCables(final Network network, final Predicate<? super Cable> filter)
         {
                 return getDepthFirstIterable(network).only(isCableRef).map(PacketSourceUtility.asCableRef).only(filter);
         }

         public static <Write,Read> void saveObjectToWriter(final Writer writer, final Write object, final SerialisationDelegate<Write,Read> delegate)
         {
                 final XMLSerialiser serialiser=new XMLSerialiser(writer);

                 serialiser.writeObject(object, "network", delegate);

                 serialiser.close();
         }

         public static Stream<Computer> getAllComputers(final Network network)
         {
                 return getAllComputers(network, Predicates.TRUE);
         }

     public static Runnable loadFromStringRef(final Network network, final String xmlString)
	{
		return new Runnable()
		{
			public void run()
			{
				loadFromString(network, xmlString);
			}
		};
	}
}
*/