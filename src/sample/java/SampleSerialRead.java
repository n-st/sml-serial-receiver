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

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.openmuc.jsml.structures.*;
import org.openmuc.jsml.tl.SML_SerialReceiver;

public class SampleSerialRead {

	private static String serverIdToString(OctetString serverId) {
		StringBuilder result = new StringBuilder();

		for (byte b : serverId.getOctetString()) {
			result.append(String.format("%02X:", b));
		}

		result.deleteCharAt(result.length() - 1);

		return result.toString();
	}

	public static void main(String[] args) throws IOException, PortInUseException, UnsupportedCommOperationException {
        System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyAMA0");
		final SML_SerialReceiver receiver = new SML_SerialReceiver();
		receiver.setupComPort("/dev/ttyAMA0");

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				try {
					receiver.close();

				} catch (IOException e) {
					System.err.println("Error while trying to close serial port: " + e.getMessage());
				}
			}
		});

		while (true) {

			SML_File smlFile = receiver.getSMLFile();
			System.out.println("Got SML_File");

			List<SML_Message> smlMessages = smlFile.getMessages();

			for (int i = 0; i < smlMessages.size(); i++) {
				SML_Message sml_message = smlMessages.get(i);
				int tag = sml_message.getMessageBody().getTag().getVal();
				if (tag == SML_MessageBody.GetListResponse) {
					SML_GetListRes resp = (SML_GetListRes) sml_message.getMessageBody().getChoice();
					SML_List smlList = resp.getValList();

					System.out.print("Server-ID: ");
					System.out.println(serverIdToString(resp.getServerId()));

					SML_ListEntry[] list = smlList.getValListEntry();

					for (SML_ListEntry entry : list) {
						int unit = entry.getUnit().getVal();
						String unitName = null;
						// Only handle entries with meaningful units
						switch (unit) {
							case SML_Unit.WATT:
								unitName = "W";
								break;
							case SML_Unit.WATT_HOUR:
								unitName = "Wh";
								break;
						}
						if (unitName != null) {
							long numericalValue;

							SML_Value value = entry.getValue();
							ASNObject obj = value.getChoice();

							if (obj.getClass().equals(Integer32.class)) {
								Integer32 val = (Integer32) obj;
								numericalValue = val.getVal();
							} else if (obj.getClass().equals(Integer64.class)) {
								Integer64 val = (Integer64) obj;
								numericalValue = val.getVal();
							} else {
								System.out.println("Got non-numerical value for an energy measurement. Skipping.");
								continue;
							}

							byte objNameBytes[] = entry.getObjName().getOctetString();
							// We need to force Java to treat the bytes as unsigned integers by AND-ing them with 0xFF
							System.out.printf("%d-%d:%d.%d.%d*%d = %.1f %s\n",
									0xFF & objNameBytes[0], 0xFF & objNameBytes[1], 0xFF & objNameBytes[2],
									0xFF & objNameBytes[3], 0xFF & objNameBytes[4], 0xFF & objNameBytes[5],
									numericalValue / 10.0,
									unitName);
						}
					}
				}
			}

			System.out.println();

		}
	}
}
