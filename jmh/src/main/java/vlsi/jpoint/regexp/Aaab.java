/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package vlsi.jpoint.regexp;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collections;
import java.util.stream.Collectors;

public class Aaab extends RegexpBenchmarkBase {
  @Param(value = {"128", "256"})
  int aSourceLength;

  @Param(value = {"3"})
  int aReps;

  @Setup
  public void setup() throws Throwable {
    patternString =
        Collections
            .nCopies(aReps, "a")
            .stream()
            .collect(Collectors.joining(".*")) + "$";

    sourceString =
        Collections
            .nCopies(aSourceLength, "a")
            .stream()
            .collect(Collectors.joining()) + "b";

    init();
  }

  @Benchmark
  public void run(Blackhole b) {
    matcher.accept(b);
  }
}
