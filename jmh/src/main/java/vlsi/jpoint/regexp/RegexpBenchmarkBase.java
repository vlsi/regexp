package vlsi.jpoint.regexp;

import com.basistech.tclre.HsrePattern;
import com.basistech.tclre.PatternFlags;
import com.basistech.tclre.RePattern;
import com.basistech.tclre.RegexException;
import com.google.common.base.Charsets;

import org.jcodings.specific.UTF8Encoding;
import org.joni.Option;
import org.joni.Regex;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@State(Scope.Thread)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class RegexpBenchmarkBase {
  @Param(value = {"java", "tcl", "joni"})
  String impl;

  String patternString;
  String sourceString;

  Consumer<Blackhole> matcher;

  public final void init() throws Throwable {
    System.out.println("patternString = " + patternString);
    System.out.println("sourceString = " + sourceString);
    if ("java".equals(impl)) {
      Pattern p = Pattern.compile(patternString);

      matcher = (Blackhole b) -> {
        b.consume(p.matcher(sourceString).matches());
      };
    } else if ("tcl".equals(impl)) {
      RePattern p = HsrePattern.compile(patternString, PatternFlags.ADVANCED);

      matcher = (Blackhole b) -> {
        b.consume(p.matcher(sourceString).matches());
      };
    } else if ("joni".equals(impl)) {
      byte[] pattern = patternString.getBytes(Charsets.UTF_8);
      Regex p = new Regex(pattern, 0, pattern.length, Option.NONE, UTF8Encoding.INSTANCE);

      matcher = (Blackhole b) -> {
        byte[] bytes = sourceString.getBytes(Charsets.UTF_8);
        b.consume(p.matcher(bytes).search(0, bytes.length, Option.DEFAULT));
      };
    } else {
      throw new IllegalArgumentException("Unknown regex engine " + impl);
    }
  }
}
