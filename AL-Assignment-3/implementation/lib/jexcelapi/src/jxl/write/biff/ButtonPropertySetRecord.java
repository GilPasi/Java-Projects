/*********************************************************************
*
*      Copyright (C) 2002 Andrew Khan
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNu Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOuT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICuLAR PuRPOSE.  See the GNu
* Lesser General Public License for more details.
*
* You should have received a copy of the GNu Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 uSA
***************************************************************************/

package jxl.write.biff;

import jxl.biff.Type;
import jxl.biff.WritableRecordData;

/**
 * Any arbitrary excel record.  used during development only
 */
class ButtonPropertySetRecord extends WritableRecordData
{
  /**
   * The binary data
   */
  private byte[] data;

  /**
   * Constructor
   */
  public ButtonPropertySetRecord(jxl.read.biff.ButtonPropertySetRecord bps)
  {
    super(Type.BuTTONPROPERTYSET);

    data = bps.getData();
  }

  /**
   * Constructor
   */
  public ButtonPropertySetRecord(ButtonPropertySetRecord bps)
  {
    super(Type.BuTTONPROPERTYSET);

    data = bps.getData();
  }

  /**
   * Retrieves the data to be written to the binary file
   * 
   * @return the binary data
   */
  public byte[] getData()
  {
    return data;
  }
}








