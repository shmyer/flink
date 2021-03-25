/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.planner.functions.aggfunctions;

import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.data.DecimalData;
import org.apache.flink.table.data.DecimalDataUtils;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.data.TimestampData;
import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.table.types.logical.BigIntType;
import org.apache.flink.table.types.logical.BooleanType;
import org.apache.flink.table.types.logical.DateType;
import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.DoubleType;
import org.apache.flink.table.types.logical.FloatType;
import org.apache.flink.table.types.logical.IntType;
import org.apache.flink.table.types.logical.LocalZonedTimestampType;
import org.apache.flink.table.types.logical.TimeType;
import org.apache.flink.table.types.logical.TimestampType;
import org.apache.flink.table.types.logical.TinyIntType;
import org.apache.flink.table.types.logical.VarCharType;
import org.apache.flink.testutils.serialization.types.ShortType;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * Test case for built-in FIRST_VALUE aggregate function.
 * This class tests `accumulate` method without order argument.
 */
@RunWith(Enclosed.class)
public final class FirstValueAggFunctionWithoutOrderTest {

	// --------------------------------------------------------------------------------------------
	// Test sets for a particular type being aggregated
	//
	// Actual tests are implemented in:
	//  - AggFunctionTestBase
	// --------------------------------------------------------------------------------------------

	/**
	 * Test for {@link TinyIntType}.
	 */
	public static final class ByteFirstValueAggFunctionWithoutOrderTest
			extends NumberFirstValueAggFunctionWithoutOrderTest<Byte> {

		@Override
		protected Byte getValue(String v) {
			return Byte.valueOf(v);
		}

		@Override
		protected AggregateFunction<Byte, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.TINYINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link ShortType}.
	 */
	public static final class ShortFirstValueAggFunctionWithoutOrderTest
			extends NumberFirstValueAggFunctionWithoutOrderTest<Short> {

		@Override
		protected Short getValue(String v) {
			return Short.valueOf(v);
		}

		@Override
		protected AggregateFunction<Short, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.SMALLINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link IntType}.
	 */
	public static final class IntFirstValueAggFunctionWithoutOrderTest
			extends NumberFirstValueAggFunctionWithoutOrderTest<Integer> {

		@Override
		protected Integer getValue(String v) {
			return Integer.valueOf(v);
		}

		@Override
		protected AggregateFunction<Integer, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.INT().getLogicalType());
		}
	}

	/**
	 * Test for {@link BigIntType}.
	 */
	public static final class LongFirstValueAggFunctionWithoutOrderTest
			extends NumberFirstValueAggFunctionWithoutOrderTest<Long> {

		@Override
		protected Long getValue(String v) {
			return Long.valueOf(v);
		}

		@Override
		protected AggregateFunction<Long, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.BIGINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link FloatType}.
	 */
	public static final class FloatFirstValueAggFunctionWithoutOrderTest
			extends NumberFirstValueAggFunctionWithoutOrderTest<Float> {

		@Override
		protected Float getValue(String v) {
			return Float.valueOf(v);
		}

		@Override
		protected AggregateFunction<Float, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.FLOAT().getLogicalType());
		}
	}

	/**
	 * Test for {@link DoubleType}.
	 */
	public static final class DoubleFirstValueAggFunctionWithoutOrderTest
			extends NumberFirstValueAggFunctionWithoutOrderTest<Double> {

		@Override
		protected Double getValue(String v) {
			return Double.valueOf(v);
		}

		@Override
		protected AggregateFunction<Double, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.DOUBLE().getLogicalType());
		}
	}

	/**
	 * Test for {@link BooleanType}.
	 */
	public static final class BooleanFirstValueAggFunctionWithoutOrderTest
			extends FirstValueAggFunctionWithoutOrderTestBase<Boolean> {

		@Override
		protected List<List<Boolean>> getInputValueSets() {
			return Arrays.asList(
					Arrays.asList(
							false,
							false,
							false
					),
					Arrays.asList(
							true,
							true,
							true
					),
					Arrays.asList(
							true,
							false,
							null,
							true,
							false,
							true,
							null
					),
					Arrays.asList(
							null,
							null,
							null
					),
					Arrays.asList(
							null,
							true
					));
		}

		@Override
		protected List<Boolean> getExpectedResults() {
			return Arrays.asList(
					false,
					true,
					true,
					null,
					true
			);
		}

		@Override
		protected AggregateFunction<Boolean, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.BOOLEAN().getLogicalType());
		}
	}

	/**
	 * Test for {@link DecimalType}.
	 */
	public static final class DecimalFirstValueAggFunctionWithoutOrderTest
			extends FirstValueAggFunctionWithoutOrderTestBase<DecimalData> {

		private int precision = 20;
		private int scale = 6;

		@Override
		protected List<List<DecimalData>> getInputValueSets() {
			return Arrays.asList(
					Arrays.asList(
							DecimalDataUtils.castFrom("1", precision, scale),
							DecimalDataUtils.castFrom("1000.000001", precision, scale),
							DecimalDataUtils.castFrom("-1", precision, scale),
							DecimalDataUtils.castFrom("-999.998999", precision, scale),
							null,
							DecimalDataUtils.castFrom("0", precision, scale),
							DecimalDataUtils.castFrom("-999.999", precision, scale),
							null,
							DecimalDataUtils.castFrom("999.999", precision, scale)
					),
					Arrays.asList(
							null,
							null,
							null,
							null,
							null
					),
					Arrays.asList(
							null,
							DecimalDataUtils.castFrom("0", precision, scale)
					)
			);
		}

		@Override
		protected List<DecimalData> getExpectedResults() {
			return Arrays.asList(
					DecimalDataUtils.castFrom("1", precision, scale),
					null,
					DecimalDataUtils.castFrom("0", precision, scale)
			);
		}

		@Override
		protected AggregateFunction<DecimalData, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.DECIMAL(precision, scale).getLogicalType());
		}
	}

	/**
	 * Test for {@link VarCharType}.
	 */
	public static final class StringFirstValueAggFunctionWithoutOrderTest
			extends FirstValueAggFunctionWithoutOrderTestBase<StringData> {

		@Override
		protected List<List<StringData>> getInputValueSets() {
			return Arrays.asList(
					Arrays.asList(
							StringData.fromString("abc"),
							StringData.fromString("def"),
							StringData.fromString("ghi"),
							null,
							StringData.fromString("jkl"),
							null,
							StringData.fromString("zzz")
					),
					Arrays.asList(
							null,
							null
					),
					Arrays.asList(
							null,
							StringData.fromString("a")
					),
					Arrays.asList(
							StringData.fromString("x"),
							null,
							StringData.fromString("e")
					)
			);
		}

		@Override
		protected List<StringData> getExpectedResults() {
			return Arrays.asList(
					StringData.fromString("abc"),
					null,
					StringData.fromString("a"),
					StringData.fromString("x")
			);
		}

		@Override
		protected AggregateFunction<StringData, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.STRING().getLogicalType());
		}
	}

	/**
	 * Test for {@link DateType}.
	 */
	public static final class DateFirstValueAggFunctionWithoutOrderTest
		extends FirstValueAggFunctionWithoutOrderTestBase<LocalDate> {

		@Override
		protected List<List<LocalDate>> getInputValueSets() {
			return Arrays.asList(
					Arrays.asList(
							LocalDate.parse("2020-11-11"),
							LocalDate.parse("2020-11-12"),
							LocalDate.parse("2020-11-13")
					),
					Arrays.asList(
							LocalDate.parse("2020-11-12"),
							LocalDate.parse("2020-11-13"),
							LocalDate.parse("2020-11-14")
					),
					Arrays.asList(
							LocalDate.parse("2020-11-12"),
							LocalDate.parse("2020-11-11"),
							null,
							LocalDate.parse("2020-11-15"),
							LocalDate.parse("2020-11-10"),
							LocalDate.parse("2020-11-09"),
							null
					),
					Arrays.asList(
							null,
							null,
							null
					),
					Arrays.asList(
							null,
							LocalDate.parse("2020-11-12")
					)
			);
		}

		@Override
		protected List<LocalDate> getExpectedResults() {
			return Arrays.asList(
					LocalDate.parse("2020-11-11"),
					LocalDate.parse("2020-11-12"),
					LocalDate.parse("2020-11-12"),
					null,
					LocalDate.parse("2020-11-12")
			);
		}

		@Override
		protected AggregateFunction<LocalDate, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.DATE().getLogicalType());
		}
	}

	/**
	 * Test for {@link TimeType}.
	 */
	public static final class TimeFirstValueAggFunctionWithoutOrderTest
		extends FirstValueAggFunctionWithoutOrderTestBase<LocalTime> {

		@Override
		protected List<List<LocalTime>> getInputValueSets() {
			return Arrays.asList(
					Arrays.asList(
							LocalTime.parse("12:00:00.123"),
							LocalTime.parse("12:45:00.345"),
							LocalTime.parse("18:30:15.678")
					),
					Arrays.asList(
							LocalTime.parse("18:00:00.123"),
							LocalTime.parse("18:45:00.345"),
							LocalTime.parse("20:30:15.678")
					),
					Arrays.asList(
							LocalTime.parse("12:00:00.123"),
							LocalTime.parse("12:45:00.345"),
							null,
							LocalTime.parse("18:00:00.123"),
							LocalTime.parse("18:45:00.345"),
							LocalTime.parse("20:30:15.678"),
							null
					),
					Arrays.asList(
							null,
							null,
							null
					),
					Arrays.asList(
							null,
							LocalTime.parse("18:00:00.345")
					)
			);
		}

		@Override
		protected List<LocalTime> getExpectedResults() {
			return Arrays.asList(
					LocalTime.parse("12:00:00.123"),
					LocalTime.parse("18:00:00.123"),
					LocalTime.parse("12:00:00.123"),
					null,
					LocalTime.parse("18:00:00.345")
			);
		}

		@Override
		protected AggregateFunction<LocalTime, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.TIME(3).getLogicalType());
		}
	}

	/**
	 * Test for {@link TimestampType}.
	 */
	public static final class TimestampFirstValueAggFunctionWithoutOrderTest
		extends FirstValueAggFunctionWithoutOrderTestBase<TimestampData> {

		@Override
		protected List<List<TimestampData>> getInputValueSets() {
			return Arrays.asList(
					Arrays.asList(
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T15:30:00.345")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T18:45:00.678"))
					),
					Arrays.asList(
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T15:30:00.345")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-14T18:45:00.678"))
					),
					Arrays.asList(
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T15:30:00.345")),
							null,
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T15:30:00.345")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-14T18:45:00.678")),
							null
					),
					Arrays.asList(
							null,
							null,
							null
					),
					Arrays.asList(
							null,
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T18:00:00.345"))
					)
			);
		}

		@Override
		protected List<TimestampData> getExpectedResults() {
			return Arrays.asList(
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T12:00:00.123")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
					null,
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T18:00:00.345"))
			);
		}

		@Override
		protected AggregateFunction<TimestampData, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.TIMESTAMP(3).getLogicalType());
		}
	}

	/**
	 * Test for {@link LocalZonedTimestampType}.
	 */
	public static final class LocalZonedTimestampFirstValueAggFunctionWithoutOrderTest
		extends FirstValueAggFunctionWithoutOrderTestBase<TimestampData> {

		@Override
		protected List<List<TimestampData>> getInputValueSets() {
			return Arrays.asList(
					Arrays.asList(
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T15:30:00.345")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T18:45:00.678"))
					),
					Arrays.asList(
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T15:30:00.345")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-14T18:45:00.678"))
					),
					Arrays.asList(
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T15:30:00.345")),
							null,
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T12:00:00.123")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T15:30:00.345")),
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-14T18:45:00.678")),
							null
					),
					Arrays.asList(
							null,
							null,
							null
					),
					Arrays.asList(
							null,
							TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T18:00:00.345"))
					)
			);
		}

		@Override
		protected List<TimestampData> getExpectedResults() {
			return Arrays.asList(
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T12:00:00.123")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
					null,
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T18:00:00.345"))
			);
		}

		@Override
		protected AggregateFunction<TimestampData, RowData> getAggregator() {
			return new FirstValueAggFunction<>(DataTypes.TIMESTAMP_WITH_LOCAL_TIME_ZONE(3).getLogicalType());
		}
	}

	// --------------------------------------------------------------------------------------------
	// This section contain base classes that provide:
	//  - common inputs
	//  - accumulator class
	// for tests declared above.
	// --------------------------------------------------------------------------------------------

	/**
	 * Test base for {@link FirstValueAggFunction} without order.
	 */
	public abstract static class FirstValueAggFunctionWithoutOrderTestBase<T>
			extends AggFunctionTestBase<T, RowData> {

		@Override
		protected Class<?> getAccClass() {
			return RowData.class;
		}
	}

	/**
	 * Test base for {@link FirstValueAggFunction} with number types.
	 */
	public abstract static class NumberFirstValueAggFunctionWithoutOrderTest<T>
			extends FirstValueAggFunctionWithoutOrderTestBase<T> {

		protected abstract T getValue(String v);

		@Override
		protected List<List<T>> getInputValueSets() {
			return Arrays.asList(
				Arrays.asList(
					getValue("1"),
					null,
					getValue("-99"),
					getValue("3"),
					null
				),
				Arrays.asList(
					null,
					null,
					null,
					null
				),
				Arrays.asList(
					null,
					getValue("10"),
					null,
					getValue("3")
				)
			);
		}

		@Override
		protected List<T> getExpectedResults() {
			return Arrays.asList(
				getValue("1"),
				null,
				getValue("10")
			);
		}
	}
}
