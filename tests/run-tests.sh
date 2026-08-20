#!/usr/bin/env bash
set -euo pipefail

test_output_dir="$(mktemp -d)"
trap 'rm -rf "${test_output_dir}"' EXIT

javac -d "${test_output_dir}" src/main/java/*.java tests/OdysseusTest.java
java -ea -cp "${test_output_dir}" OdysseusTest
