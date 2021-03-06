/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.parser.parser;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.rookit.parser.Parser;
import org.rookit.parser.result.Result;


@SuppressWarnings("javadoc")
public class ParserTest {
	
	private static final RandomStringGenerator RANDOM_STRING_GENERATOR =
			new RandomStringGenerator.Builder().build();
	
	@Test
	public final void testCreateConfig() {
		assertThat(Parser.createConfiguration(SomeRandomResult.class)).isNotNull();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public final void testCreateConfigNullResultClass() {
		Parser.createConfiguration(null);
	}
	
	private static class SomeRandomResult implements Result<String> {

		@Override
		public String build() {
			return RANDOM_STRING_GENERATOR.generate(20);
		}

		@Override
		public int compareTo(Result<String> o) {
			return o.build().compareTo(this.build());
		}

		@Override
		public int getScore() {
			return 30;
		}

		@Override
		public void setScore(int score) {
			// TODO Auto-generated field stub
		}
		
	}

}
