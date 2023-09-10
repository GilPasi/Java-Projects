/*********************************************************************
*
*      Copyright (C) 2003 Andrew Khan
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

package jxl.read.biff;

import jxl.common.Logger;

import jxl.biff.IntegerHelper;
import jxl.biff.RecordData;

/**
 * Contains the cell dimensions of this worksheet
 */
class PaneRecord extends RecordData
{
  /**
   * The logger
   */
  private static Logger logger = Logger.getLogger(PaneRecord.class);

  /**
   * The number of rows visible in the top left pane
   */
  private int rowsvisible;
  /**
   * The number of columns visible in the top left pane
   */
  private int columnsvisible;

  /**
   * Constructs the dimensions from the raw data
   *
   * @param t the raw data
   */
  public PaneRecord(Record t)
  {
    super(t);
    byte[] data = t.getData();

    columnsvisible = IntegerHelper.getInt(data[0], data[1]);
    rowsvisible = IntegerHelper.getInt(data[2], data[3]);
  }

  /**
   * Accessor for the number of rows in the top left pane
   *
   * @return the number of rows visible in the top left pane
   */
  public final int getRowsvisible()
  {
    return rowsvisible;
  }

  /**
   * Accessor for the numbe rof columns visible in the top left pane
   *
   * @return the number of columns visible in the top left pane
   */
  public final int getColumnsvisible()
  {
    return columnsvisible;
  }
}







