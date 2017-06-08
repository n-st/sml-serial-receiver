/*
 * Copyright 2009-14 Fraunhofer ISE
 *
 * This file is part of jSML.
 * For more information visit http://www.openmuc.org
 *
 * jSML is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jSML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jSML.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.jsml.structures;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class EncodingDecodingTest {

	@Test
	public void encodeListOfSMLTreeWithManyElements() {
		ByteArrayOutputStream bs = new ByteArrayOutputStream(50);
		DataOutputStream os = new DataOutputStream(bs);
		SML_Tree[] sml_Trees = new SML_Tree[25];
		for (int i = 0; i < 25; i++) {
			sml_Trees[i] = new SML_Tree(new OctetString("test"), null, null);
		}
		List_of_SML_Tree list_of_SML_Tree = new List_of_SML_Tree(sml_Trees);

		try {
			list_of_SML_Tree.code(os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertEquals(bs.toByteArray()[0], (byte) 0xf1);
		Assert.assertEquals(bs.toByteArray()[1], (byte) 0x09);

	}

}
