package ipsim.network.conformance;

import fpeas.function.*;
import ipsim.network.*;
import static ipsim.network.conformance.ConformanceTestsUtility.*;
import ipsim.util.*;
import ipsim.util.Collections;

import java.util.*;

public class ConformanceTests
{
	public static ResultsAndSummaryAndPercent allChecks(final Network network)
	{
		final Collection<Function<Network, CheckResult>> checks=createNetworkCheck();
		final StringBuilder answer=new StringBuilder();

		double totalPercent=100;

		final List<CheckResult> checkResults=Collections.arrayList();

		for (final Function<Network,CheckResult> check: checks)
		{
			final CheckResult result=check.run(network);

			final int percent=result.percent;

			totalPercent*=percent;
			totalPercent/=100;

			final Stream<String> summary=result.summary;

			if (!(100==percent))
			{
				answer.append(summary.isEmpty() ? "" : summary.car());

				checkResults.add(result);
				answer.append(".\n");
			}
		}

		return new ResultsAndSummaryAndPercent(Stream.fromIterable(checkResults),answer.toString(),(int)Math.round(totalPercent));
	}

	public static final class ResultsAndSummaryAndPercent
	{
		public final Stream<CheckResult> results;
		public final String summary;
		public final int percent;

		public ResultsAndSummaryAndPercent(final Stream<CheckResult> results,final String summary,final int percent)
		{
			this.results=results;
			this.summary=summary;
			this.percent=percent;
		}
	}
}