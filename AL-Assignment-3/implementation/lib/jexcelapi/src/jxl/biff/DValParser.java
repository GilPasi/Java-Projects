/*********************************************************************
*
*      Copyright (C) 2004 Andrew Khan
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

package jxl.biff;

import jxl.common.Logger;

import jxl.biff.IntegerHelper;

/**
 * Class which parses the binary data associated with Data validity (Dval)
 * setting
 */
public class DvalParser
{
  /**
   * The logger
   */
  private static Logger logger = Logger.getLogger(DvalParser.class);

  // The option masks
  private static int PROMPT_BOX_vISIBLE_MASK = 0x1;
  private static int PROMPT_BOX_AT_CELL_MASK = 0x2;
  private static int vALIDITY_DATA_CACHED_MASK = 0x4;

  /**
   * Prompt box visible
   */
  private boolean promptBoxvisible;

  /**
   * Empty cells allowed
   */
  private boolean promptBoxAtCell;

  /**
   * Cell validity data cached in following Dv records
   */
  private boolean validityDataCached;

  /**
   * The number of following Dv records
   */
  private int numDvRecords;

  /**
   * The object id of the associated down arrow
   */
  private int objectId;

  /**
   * Constructor
   */
  public DvalParser(byte[] data)
  {
    int options = IntegerHelper.getInt(data[0], data[1]);

    promptBoxvisible = (options & PROMPT_BOX_vISIBLE_MASK) != 0;
    promptBoxAtCell = (options & PROMPT_BOX_AT_CELL_MASK) != 0;
    validityDataCached = (options & vALIDITY_DATA_CACHED_MASK) != 0;
    
    objectId = IntegerHelper.getInt(data[10], data[11], data[12], data[13]);
    numDvRecords = IntegerHelper.getInt(data[14], data[15], 
                                        data[16], data[17]);    
  }

  /**
   * Constructor
   */
  public DvalParser(int objid, int num)
  {
    objectId = objid;
    numDvRecords = num;
    validityDataCached = true;
  }

  /**
   * Gets the data
   */
  public byte[] getData()
  {
    byte[] data = new byte[18];

    int options = 0;
    
    if (promptBoxvisible)
    {
      options |= PROMPT_BOX_vISIBLE_MASK;
    }

    if (promptBoxAtCell)
    {
      options |= PROMPT_BOX_AT_CELL_MASK;
    }

    if (validityDataCached)
    {
      options |= vALIDITY_DATA_CACHED_MASK;
    }

    IntegerHelper.getTwoBytes(options, data, 0);

    IntegerHelper.getFourBytes(objectId, data, 10);

    IntegerHelper.getFourBytes(numDvRecords, data, 14);

    return data;
  }

  /**
   * Called when a remove row or column results in one of Dv records being 
   * removed
   */
  public void dvRemoved()
  {
    numDvRecords--;
  }

  /**
   * Accessor for the number of Dv records
   *
   * @return the number of Dv records for this list
   */
  public int getNumberOfDvRecords()
  {
    return numDvRecords;
  }

  /**
   * Accessor for the object id
   *
   * @return the object id
   */
  public int getObjectId()
  {
    return objectId;
  }

  /**
   * Called when adding a Dv record on a copied Dval
   */
  public void dvAdded()
  {
    numDvRecords++;
  }
}
