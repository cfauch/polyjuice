/*
 * Copyright 2020 Claire Fauch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.code.fauch.polyjuice;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author c.fauch
 *
 */
public class AbsFixedSizeContentTest {

    /**
     * EXPECTED:
     * +---2bytes---+---3bytes---+
     * |     3      |     yes    |
     * +------------+------------+
     */
    @Test
    public void testEncodeWithNullExpectedSize() {
        final FixedSizeContentImpl fixedSizeContent = new FixedSizeContentImpl();
        fixedSizeContent.message("yes");
        byte[] yes = "yes".getBytes(StandardCharsets.UTF_8);
        byte[] expecteds = new byte[] {0, 3, yes[0], yes[1], yes[2]};
        Assert.assertArrayEquals(expecteds, fixedSizeContent.getBytes());
    }

    @Test
    public void testEncodeWithSizeLess() {
        final FixedSizeContentImpl fixedSizeContent = new FixedSizeContentImpl();
        fixedSizeContent.message("yes");
        fixedSizeContent.setExpectedSize(3);
        byte[] yes = "yes".getBytes(StandardCharsets.UTF_8);
        byte[] expecteds = new byte[] {0, 3, yes[0]};
        Assert.assertArrayEquals(expecteds, fixedSizeContent.getBytes());
    }
    
    @Test
    public void testEncodeWithSizeMore() {
        final FixedSizeContentImpl fixedSizeContent = new FixedSizeContentImpl();
        fixedSizeContent.message("yes");
        fixedSizeContent.setExpectedSize(7);
        byte[] yes = "yes".getBytes(StandardCharsets.UTF_8);
        byte[] expecteds = new byte[] {0, 3, yes[0], yes[1], yes[2], 0, 0};
        Assert.assertArrayEquals(expecteds, fixedSizeContent.getBytes());
    }
}
