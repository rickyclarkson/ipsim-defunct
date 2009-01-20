package com.rickyclarkson.testsuite;

import fpeas.lazy.*;

import static java.lang.System.*;

public final class UnitTestUtility
{
	public static boolean runTests(final Iterable<Boolean> tests)
	{
		int passed=0;
		int failed=0;

		for (final Boolean test: tests)
		{
                    if (test)
                        {
                            out.println(test.toString()+" passed");
                            passed++;
                        }
                    else
                        {
                            out.println(test.toString()+" failed");
                            failed++;
                        }
                }
		
		out.println(passed+" passed");
		out.println(failed+" failed");

		return failed==0;
	}
}
