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

import jxl.biff.IntegerHelper;
import jxl.biff.Type;
import jxl.biff.WritableRecordData;

/**
 * Contains the window attributes for a worksheet
 */
class PaneRecord extends WritableRecordData
{
  /**
   * The number of rows visible in the top left pane
   */
  private int rowsvisible;
  /**
   * The number of columns visible in the top left pane
   */
  private int columnsvisible;

  /**
   * The pane codes
   */
  private final static int topLeftPane = 0x3;
  private final static int bottomLeftPane = 0x2;
  private final static int topRightPane = 0x1;
  private final static int bottomRightPane = 0x0;

  /**
   * Code

  /**
   * Constructor
   */
  public PaneRecord(int cols, int rows)
  {
    super(Type.PANE);

    rowsvisible = rows;
    columnsvisible = cols;
  }

  /**
   * Gets the binary data for output to file
   * 
   * @return the binary data
   */
  public byte[] getData()
  {
    byte[] data = new byte[10];

    // The x position
    IntegerHelper.getTwoBytes(columnsvisible, data, 0);

    // The y position
    IntegerHelper.getTwoBytes(rowsvisible, data, 2);

    // The top row visible in the bottom pane
    if (rowsvisible > 0)
    {
      IntegerHelper.getTwoBytes(rowsvisible, data, 4);
    }

    // The left most column visible in the right pane
    if (columnsvisible > 0)
    {
      IntegerHelper.getTwoBytes(columnsvisible, data, 6);
    }

    // The active pane
    int activePane = topLeftPane;

    if (rowsvisible > 0 && columnsvisible == 0)
    {
      activePane = bottomLeftPane;
    }
    else if (rowsvisible == 0 && columnsvisible > 0)
    {
      activePane = topRightPane;
    }
    else if (rowsvisible > 0 && columnsvisible > 0)
    {
      activePane = bottomRightPane;
    }
    // always present
    IntegerHelper.getTwoBytes(activePane, data, 8);

    return data;
  }
}
