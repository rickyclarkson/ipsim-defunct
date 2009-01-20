package ipsim.network.conformance;
/*
import static ipsim.network.conformance.TypicalScores.*;
import ipsim.network.connectivity.*;
import static ipsim.util.Collections.*;
import ipsim.util.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CheckResultUtility
{
	public static CheckResult fine()
	{
		return new CheckResult(NONE, new Stream<String>(),new Stream<PacketSource>());
	}

	@NotNull
	public static CheckResult pessimisticMerge(final Iterable<CheckResult> iterable)
	{
		CheckResult minPercent=null;

		for (final CheckResult result: iterable)
			if (minPercent==null || result.percent<minPercent.percent)
				minPercent=result;

		final List<String> list1=arrayList();
		final List<PacketSource> list2=arrayList();

		return minPercent==null ? fine() : minPercent;
	}
        }*/