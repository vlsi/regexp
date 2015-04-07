package vlsi.jpoint.regexp;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collections;
import java.util.stream.Collectors;

public class Bqbqbb extends RegexpBenchmarkBase {
  @Param(value = {"14", "21", "28"})
  int bSourceLength;

  @Setup
  public void setup() throws Throwable {
    sourceString =
        Collections.nCopies(bSourceLength, "b")
            .stream()
            .collect(Collectors.joining());
    patternString =
        "^"
            + Collections.nCopies(bSourceLength, "b")
            .stream()
            .collect(Collectors.joining("?", "", "?"))
            + sourceString + "$";

    init();
  }

  @Benchmark
  public void run(Blackhole b) {
    matcher.accept(b);
  }
}
