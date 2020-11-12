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
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.data.TimestampData;
import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.table.planner.functions.aggfunctions.FirstValueWithRetractAggFunction.FirstValueWithRetractAccumulator;
import org.apache.flink.table.types.logical.BigIntType;
import org.apache.flink.table.types.logical.BooleanType;
import org.apache.flink.table.types.logical.DateType;
import org.apache.flink.table.types.logical.DecimalType;
import org.apache.flink.table.types.logical.DoubleType;
import org.apache.flink.table.types.logical.FloatType;
import org.apache.flink.table.types.logical.IntType;
import org.apache.flink.table.types.logical.TimeType;
import org.apache.flink.table.types.logical.TimestampType;
import org.apache.flink.table.types.logical.TinyIntType;
import org.apache.flink.table.types.logical.VarCharType;
import org.apache.flink.testutils.serialization.types.ShortType;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * Test case for built-in FIRST_VALUE with retract aggregate function.
 * This class tests `accumulate` method with order argument.
 */
@RunWith(Enclosed.class)
public final class FirstValueWithRetractAggFunctionWithOrderTest {

	// --------------------------------------------------------------------------------------------
	// Test sets for a particular type being aggregated
	//
	// Actual tests are implemented in:
	//  - FirstLastValueAggFunctionWithOrderTestBase -> tests specific for FirstValue and LastValue
	//  - AggFunctionTestBase -> tests that apply to all aggregate functions
	// --------------------------------------------------------------------------------------------

	/**
	 * Test for {@link TinyIntType}.
	 */
	public static final class ByteFirstValueWithRetractAggFunctionWithOrderTest
			extends NumberFirstValueWithRetractAggFunctionWithOrderTestBase<Byte> {

		@Override
		protected Byte getValue(String v) {
			return Byte.valueOf(v);
		}

		@Override
		protected AggregateFunction<Byte, FirstValueWithRetractAccumulator<Byte>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.TINYINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link ShortType}.
	 */
	public static final class ShortFirstValueWithRetractAggFunctionWithOrderTest
			extends NumberFirstValueWithRetractAggFunctionWithOrderTestBase<Short> {

		@Override
		protected Short getValue(String v) {
			return Short.valueOf(v);
		}

		@Override
		protected AggregateFunction<Short, FirstValueWithRetractAccumulator<Short>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.SMALLINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link IntType}.
	 */
	public static final class IntFirstValueWithRetractAggFunctionWithOrderTest
			extends NumberFirstValueWithRetractAggFunctionWithOrderTestBase<Integer> {

		@Override
		protected Integer getValue(String v) {
			return Integer.valueOf(v);
		}

		@Override
		protected AggregateFunction<Integer, FirstValueWithRetractAccumulator<Integer>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.INT().getLogicalType());
		}
	}

	/**
	 * Test for {@link BigIntType}.
	 */
	public static final class LongFirstValueWithRetractAggFunctionWithOrderTest
			extends NumberFirstValueWithRetractAggFunctionWithOrderTestBase<Long> {

		@Override
		protected Long getValue(String v) {
			return Long.valueOf(v);
		}

		@Override
		protected AggregateFunction<Long, FirstValueWithRetractAccumulator<Long>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.BIGINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link FloatType}.
	 */
	public static final class FloatFirstValueWithRetractAggFunctionWithOrderTest
			extends NumberFirstValueWithRetractAggFunctionWithOrderTestBase<Float> {

		@Override
		protected Float getValue(String v) {
			return Float.valueOf(v);
		}

		@Override
		protected AggregateFunction<Float, FirstValueWithRetractAccumulator<Float>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.FLOAT().getLogicalType());
		}
	}

	/**
	 * Test for {@link DoubleType}.
	 */
	public static final class DoubleFirstValueWithRetractAggFunctionWithOrderTest
			extends NumberFirstValueWithRetractAggFunctionWithOrderTestBase<Double> {

		@Override
		protected Double getValue(String v) {
			return Double.valueOf(v);
		}

		@Override
		protected AggregateFunction<Double, FirstValueWithRetractAccumulator<Double>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.DOUBLE().getLogicalType());
		}
	}

	/**
	 * Test for {@link BooleanType}.
	 */
	public static final class BooleanFirstValueWithRetractAggFunctionWithOrderTest
			extends FirstValueWithRetractAggFunctionWithOrderTestBase<Boolean> {

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
		protected List<List<Long>> getInputOrderSets() {
			return Arrays.asList(
					Arrays.asList(
							6L,
							2L,
							3L
					),
					Arrays.asList(
							1L,
							2L,
							3L
					),
					Arrays.asList(
							10L,
							2L,
							5L,
							11L,
							3L,
							7L,
							5L
					),
					Arrays.asList(
							6L,
							9L,
							5L
					),
					Arrays.asList(
							4L,
							3L
					)
			);
		}

		@Override
		protected List<Boolean> getExpectedResults() {
			return Arrays.asList(
					false,
					true,
					false,
					null,
					true
			);
		}

		@Override
		protected AggregateFunction<Boolean, FirstValueWithRetractAccumulator<Boolean>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.BOOLEAN().getLogicalType());
		}
	}

	/**
	 * Test for {@link DecimalType}.
	 */
	public static final class DecimalFirstValueWithRetractAggFunctionWithOrderTest
			extends FirstValueWithRetractAggFunctionWithOrderTestBase<DecimalData> {

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
		protected List<List<Long>> getInputOrderSets() {
			return Arrays.asList(
					Arrays.asList(
							10L,
							2L,
							1L,
							5L,
							null,
							3L,
							1L,
							5L,
							2L
					),
					Arrays.asList(
							6L,
							5L,
							null,
							8L,
							null
					),
					Arrays.asList(
							8L,
							6L
					)
			);
		}

		@Override
		protected List<DecimalData> getExpectedResults() {
			return Arrays.asList(
					DecimalDataUtils.castFrom("-1", precision, scale),
					null,
					DecimalDataUtils.castFrom("0", precision, scale)
			);
		}

		@Override
		protected AggregateFunction<DecimalData, FirstValueWithRetractAccumulator<DecimalData>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.DECIMAL(precision, scale).getLogicalType());
		}
	}

	/**
	 * Test for {@link VarCharType}.
	 */
	public static final class StringFirstValueWithRetractAggFunctionWithOrderTest
			extends FirstValueWithRetractAggFunctionWithOrderTestBase<StringData> {

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
		protected List<List<Long>> getInputOrderSets() {
			return Arrays.asList(
					Arrays.asList(
							10L,
							2L,
							5L,
							null,
							3L,
							1L,
							5L
					),
					Arrays.asList(
							6L,
							5L
					),
					Arrays.asList(
							8L,
							6L
					),
					Arrays.asList(
							6L,
							4L,
							3L
					)
			);
		}

		@Override
		protected List<StringData> getExpectedResults() {
			return Arrays.asList(
					StringData.fromString("def"),
					null,
					StringData.fromString("a"),
					StringData.fromString("e")
			);
		}

		@Override
		protected AggregateFunction<StringData, FirstValueWithRetractAccumulator<StringData>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.STRING().getLogicalType());
		}
	}

	/**
	 * Test for {@link DateType}.
	 */
	public static final class DateFirstValueWithRetractAggFunctionWithOrderTest
		extends FirstValueWithRetractAggFunctionWithOrderTestBase<LocalDate> {

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
				));
		}

		@Override
		protected List<List<Long>> getInputOrderSets() {
			return Arrays.asList(
				Arrays.asList(
					6L,
					2L,
					3L
				),
				Arrays.asList(
					1L,
					2L,
					3L
				),
				Arrays.asList(
					10L,
					2L,
					5L,
					3L,
					11L,
					7L,
					5L
				),
				Arrays.asList(
					6L,
					9L,
					5L
				),
				Arrays.asList(
					4L,
					3L
				)
			);
		}

		@Override
		protected List<LocalDate> getExpectedResults() {
			return Arrays.asList(
				LocalDate.parse("2020-11-12"),
				LocalDate.parse("2020-11-12"),
				LocalDate.parse("2020-11-11"),
				null,
				LocalDate.parse("2020-11-12")
			);
		}

		@Override
		protected AggregateFunction<LocalDate, FirstValueWithRetractAccumulator<LocalDate>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.DATE().getLogicalType());
		}
	}

	/**
	 * Test for {@link TimeType}.
	 */
	public static final class TimeFirstValueWithRetractAggFunctionWithOrderTest
		extends FirstValueWithRetractAggFunctionWithOrderTestBase<LocalTime> {

		private int precision = 3;

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
				));
		}

		@Override
		protected List<List<Long>> getInputOrderSets() {
			return Arrays.asList(
				Arrays.asList(
					6L,
					2L,
					3L
				),
				Arrays.asList(
					1L,
					2L,
					3L
				),
				Arrays.asList(
					10L,
					2L,
					5L,
					3L,
					11L,
					7L,
					5L
				),
				Arrays.asList(
					6L,
					9L,
					5L
				),
				Arrays.asList(
					4L,
					3L
				)
			);
		}

		@Override
		protected List<LocalTime> getExpectedResults() {
			return Arrays.asList(
				LocalTime.parse("12:45:00.345"),
				LocalTime.parse("18:00:00.123"),
				LocalTime.parse("12:45:00.345"),
				null,
				LocalTime.parse("18:00:00.345")
			);
		}

		@Override
		protected AggregateFunction<LocalTime, FirstValueWithRetractAccumulator<LocalTime>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.TIME(precision).getLogicalType());
		}
	}

	/**
	 * Test for {@link TimestampType}.
	 */
	public static final class TimestampFirstValueWithRetractAggFunctionWithOrderTest
		extends FirstValueWithRetractAggFunctionWithOrderTestBase<TimestampData> {

		private int precision = 3;

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
				));
		}

		@Override
		protected List<List<Long>> getInputOrderSets() {
			return Arrays.asList(
				Arrays.asList(
					6L,
					2L,
					3L
				),
				Arrays.asList(
					1L,
					2L,
					3L
				),
				Arrays.asList(
					10L,
					2L,
					5L,
					3L,
					11L,
					7L,
					5L
				),
				Arrays.asList(
					6L,
					9L,
					5L
				),
				Arrays.asList(
					4L,
					3L
				)
			);
		}

		@Override
		protected List<TimestampData> getExpectedResults() {
			return Arrays.asList(
				TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T15:30:00.345")),
				TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T12:00:00.123")),
				TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T15:30:00.345")),
				null,
				TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T18:00:00.345"))
			);
		}

		@Override
		protected AggregateFunction<TimestampData, FirstValueWithRetractAccumulator<TimestampData>> getAggregator() {
			return new FirstValueWithRetractAggFunction<>(DataTypes.TIMESTAMP(precision).getLogicalType());
		}
	}

	// --------------------------------------------------------------------------------------------
	// This section contain base classes that provide common inputs for tests declared above.
	// --------------------------------------------------------------------------------------------

	/**
	 * Test base for {@link FirstValueWithRetractAggFunction} with order.
	 */
	public abstract static class FirstValueWithRetractAggFunctionWithOrderTestBase<T>
			extends FirstLastValueAggFunctionWithOrderTestBase<T, FirstValueWithRetractAccumulator<T>> {

		@Override
		protected Class<?> getAccClass() {
			return FirstValueWithRetractAccumulator.class;
		}

		@Override
		protected Method getRetractFunc() throws NoSuchMethodException {
			return getAggregator().getClass().getMethod("retract", getAccClass(), Object.class, Long.class);
		}
	}

	/**
	 * Test base for {@link FirstValueWithRetractAggFunction} with number types.
	 */
	public abstract static class NumberFirstValueWithRetractAggFunctionWithOrderTestBase<T>
			extends FirstValueWithRetractAggFunctionWithOrderTestBase<T> {

		protected abstract T getValue(String v);

		@Override
		protected List<List<T>> getInputValueSets() {
			return Arrays.asList(
				Arrays.asList(
					getValue("1"),
					null,
					getValue("-99"),
					getValue("3"),
					null,
					getValue("3"),
					getValue("2"),
					getValue("-99")
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
					getValue("5")
				)
			);
		}

		@Override
		protected List<List<Long>> getInputOrderSets() {
			return Arrays.asList(
				Arrays.asList(
					10L,
					2L,
					5L,
					6L,
					11L,
					3L,
					7L,
					5L
				),
				Arrays.asList(
					8L,
					6L,
					9L,
					5L
				),
				Arrays.asList(
					null,
					6L,
					4L,
					3L
				)
			);
		}

		@Override
		protected List<T> getExpectedResults() {
			return Arrays.asList(
				getValue("3"),
				null,
				getValue("5")
			);
		}
	}
}
