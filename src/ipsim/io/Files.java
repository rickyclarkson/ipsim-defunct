package ipsim.io;

import fpeas.function.*;
import org.jetbrains.annotations.*;

import java.io.*;

public class Files
{
	public static Function<File, String> getNameRef=new Function<File, String>()
	{
		@NotNull
		public String run(@NotNull final File file)
		{
			return file.getName();
		}
	};
}
