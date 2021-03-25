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
 * Test case for built-in LastValue aggregate function.
 * This class tests `accumulate` method with order argument.
 */
@RunWith(Enclosed.class)
public final class LastValueAggFunctionWithOrderTest {

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
	public static final class ByteLastValueAggFunctionWithOrderTest
			extends NumberLastValueAggFunctionWithOrderTestBase<Byte> {

		@Override
		protected Byte getValue(String v) {
			return Byte.valueOf(v);
		}

		@Override
		protected AggregateFunction<Byte, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.TINYINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link ShortType}.
	 */
	public static final class ShortLastValueAggFunctionWithOrderTest
			extends NumberLastValueAggFunctionWithOrderTestBase<Short> {

		@Override
		protected Short getValue(String v) {
			return Short.valueOf(v);
		}

		@Override
		protected AggregateFunction<Short, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.SMALLINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link IntType}.
	 */
	public static final class IntLastValueAggFunctionWithOrderTest
			extends NumberLastValueAggFunctionWithOrderTestBase<Integer> {

		@Override
		protected Integer getValue(String v) {
			return Integer.valueOf(v);
		}

		@Override
		protected AggregateFunction<Integer, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.INT().getLogicalType());
		}
	}

	/**
	 * Test for {@link BigIntType}.
	 */
	public static final class LongLastValueAggFunctionWithOrderTest
			extends NumberLastValueAggFunctionWithOrderTestBase<Long> {

		@Override
		protected Long getValue(String v) {
			return Long.valueOf(v);
		}

		@Override
		protected AggregateFunction<Long, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.BIGINT().getLogicalType());
		}
	}

	/**
	 * Test for {@link FloatType}.
	 */
	public static final class FloatLastValueAggFunctionWithOrderTest
			extends NumberLastValueAggFunctionWithOrderTestBase<Float> {

		@Override
		protected Float getValue(String v) {
			return Float.valueOf(v);
		}

		@Override
		protected AggregateFunction<Float, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.FLOAT().getLogicalType());
		}
	}

	/**
	 * Test for {@link DoubleType}.
	 */
	public static final class DoubleLastValueAggFunctionWithOrderTest
			extends NumberLastValueAggFunctionWithOrderTestBase<Double> {

		@Override
		protected Double getValue(String v) {
			return Double.valueOf(v);
		}

		@Override
		protected AggregateFunction<Double, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.DOUBLE().getLogicalType());
		}
	}

	/**
	 * Test for {@link BooleanType}.
	 */
	public static final class BooleanLastValueAggFunctionWithOrderTest
			extends LastValueAggFunctionWithOrderTestBase<Boolean> {

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
		protected AggregateFunction<Boolean, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.BOOLEAN().getLogicalType());
		}
	}

	/**
	 * Test for {@link DecimalType}.
	 */
	public static final class DecimalLastValueAggFunctionWithOrderTest
			extends LastValueAggFunctionWithOrderTestBase<DecimalData> {

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
					DecimalDataUtils.castFrom("1", precision, scale),
					null,
					DecimalDataUtils.castFrom("0", precision, scale)
			);
		}

		@Override
		protected AggregateFunction<DecimalData, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.DECIMAL(precision, scale).getLogicalType());
		}
	}

	/**
	 * Test for {@link VarCharType}.
	 */
	public static final class StringLastValueAggFunctionWithOrderTest
			extends LastValueAggFunctionWithOrderTestBase<StringData> {

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
							15L
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
					StringData.fromString("zzz"),
					null,
					StringData.fromString("a"),
					StringData.fromString("x")
			);
		}

		@Override
		protected AggregateFunction<StringData, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.STRING().getLogicalType());
		}
	}

	/**
	 * Test for {@link DateType}.
	 */
	public static final class DateLastValueAggFunctionWithOrderTest
		extends LastValueAggFunctionWithOrderTestBase<LocalDate> {

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
					LocalDate.parse("2020-11-11"),
					LocalDate.parse("2020-11-14"),
					LocalDate.parse("2020-11-10"),
					null,
					LocalDate.parse("2020-11-12")
			);
		}

		@Override
		protected AggregateFunction<LocalDate, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.DATE().getLogicalType());
		}
	}

	/**
	 * Test for {@link TimeType}.
	 */
	public static final class TimeLastValueAggFunctionWithOrderTest
		extends LastValueAggFunctionWithOrderTestBase<LocalTime> {

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
					LocalTime.parse("12:00:00.123"),
					LocalTime.parse("20:30:15.678"),
					LocalTime.parse("18:45:00.345"),
					null,
					LocalTime.parse("18:00:00.345")
			);
		}

		@Override
		protected AggregateFunction<LocalTime, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.TIME(3).getLogicalType());
		}
	}

	/**
	 * Test for {@link TimestampType}.
	 */
	public static final class TimestampLastValueAggFunctionWithOrderTest
		extends LastValueAggFunctionWithOrderTestBase<TimestampData> {

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
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-14T18:45:00.678")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T15:30:00.345")),
					null,
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T18:00:00.345"))
			);
		}

		@Override
		protected AggregateFunction<TimestampData, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.TIMESTAMP(3).getLogicalType());
		}
	}

	/**
	 * Test for {@link LocalZonedTimestampType}.
	 */
	public static final class LocalZonedTimestampLastValueAggFunctionWithOrderTest
		extends LastValueAggFunctionWithOrderTestBase<TimestampData> {

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
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-11T12:00:00.123")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-14T18:45:00.678")),
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-13T15:30:00.345")),
					null,
					TimestampData.fromLocalDateTime(LocalDateTime.parse("2020-11-12T18:00:00.345"))
			);
		}

		@Override
		protected AggregateFunction<TimestampData, RowData> getAggregator() {
			return new LastValueAggFunction<>(DataTypes.TIMESTAMP_WITH_LOCAL_TIME_ZONE(3).getLogicalType());
		}
	}

	// --------------------------------------------------------------------------------------------
	// This section contain base classes that provide common inputs for tests declared above.
	// --------------------------------------------------------------------------------------------

	/**
	 * Test base for {@link LastValueAggFunction}.
	 */
	public abstract static class LastValueAggFunctionWithOrderTestBase<T>
			extends FirstLastValueAggFunctionWithOrderTestBase<T, RowData> {

		@Override
		protected Class<?> getAccClass() {
			return RowData.class;
		}
	}

	/**
	 * Test base for {@link LastValueAggFunction} with number types.
	 */
	public abstract static class NumberLastValueAggFunctionWithOrderTestBase<T>
			extends LastValueAggFunctionWithOrderTestBase<T> {

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
					17L,
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
				getValue("2"),
				null,
				getValue("10")
			);
		}
	}
}
