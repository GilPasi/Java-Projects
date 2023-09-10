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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import jxl.common.Assert;
import jxl.common.Logger;

import jxl.WorkbookSettings;
import jxl.biff.formula.ExternalSheet;
import jxl.write.biff.File;


/**
 * Class which encapsulates a data validation.  This encapsulates the
 * base DvAL record (DatavalidityListRecord) and all the individual Dv
 * (DatavaliditySettingsRecord) records
 */
public class Datavalidation
{
  /**
   * The logger
   */
  private static Logger logger = Logger.getLogger(Datavalidation.class);

  /** 
   * The data validity list
   */
  private DatavalidityListRecord validityList;

  /**
   * The list of data validity (Dv) records
   */
  private ArrayList validitySettings;

  /**
   * Handle to the workbook
   */
  private WorkbookMethods workbook;

  /**

   * Handle to the external sheet
   */
  private ExternalSheet externalSheet;

  /**
   * Handle to the workbook settings
   */
  private WorkbookSettings workbookSettings;

  /**
   * The object id of the combo box used for drop downs
   */
  private int comboBoxObjectId;

  /**
   * Indicates whether this was copied
   */
  private boolean copied;

  public static final int DEFAuLT_OBJECT_ID = 0xffffffff;
  private static final int MAX_NO_OF_vALIDITY_SETTINGS = 0xfffd;

  /**
   * Constructor
   */
  public Datavalidation(DatavalidityListRecord dvlr)
  {
    validityList = dvlr;
    validitySettings = new ArrayList(validityList.getNumberOfSettings());
    copied = false;
  }

  /**
   * Constructor used to create writable data validations
   */
  public Datavalidation(int objId,
                        ExternalSheet es,
                        WorkbookMethods wm, 
                        WorkbookSettings ws )
  {
    workbook = wm;
    externalSheet = es;
    workbookSettings = ws;
    validitySettings = new ArrayList();
    comboBoxObjectId = objId;
    copied = false;
  }

  /**
   * Copy constructor used to copy from read to write
   */
  public Datavalidation(Datavalidation dv,
                        ExternalSheet es,
                        WorkbookMethods wm, 
                        WorkbookSettings ws )
  {
    workbook = wm;
    externalSheet = es;
    workbookSettings = ws;
    copied = true;
    validityList = new DatavalidityListRecord(dv.getDatavalidityList());

    validitySettings = new ArrayList();
    DatavaliditySettingsRecord[] settings = dv.getDatavaliditySettings();

    for (int i = 0; i < settings.length ; i++)
    {
      validitySettings.add(new DatavaliditySettingsRecord(settings[i],
                                                          externalSheet,
                                                          workbook,
                                                          workbookSettings));
    }
  }

  /**
   * Adds a new settings object to this data validation
   */
  public void add(DatavaliditySettingsRecord dvsr)
  {
    validitySettings.add(dvsr);
    dvsr.setDatavalidation(this);

    if (copied)
    {
      // adding a writable dv record to a copied validity list
      Assert.verify(validityList != null);
      validityList.dvAdded();
    }
  }

  /**
   * Accessor for the validity list.  used when copying sheets
   */
  public DatavalidityListRecord getDatavalidityList()
  {
    return validityList;
  }

  /**
   * Accessor for the validity settings.  used when copying sheets
   */
  public DatavaliditySettingsRecord[] getDatavaliditySettings()
  {
    DatavaliditySettingsRecord[] dvlr = new DatavaliditySettingsRecord[0];
    return (DatavaliditySettingsRecord[]) validitySettings.toArray(dvlr);
  }

  /**
   * Writes out the data validation
   * 
   * @exception IOException 
   * @param outputFile the output file
   */
  public void write(File outputFile) throws IOException
  {
    if (validitySettings.size() > MAX_NO_OF_vALIDITY_SETTINGS)
    {
      logger.warn("Maximum number of data validations exceeded - " +
                  "truncating...");
      validitySettings = new ArrayList
        (validitySettings.subList(0, MAX_NO_OF_vALIDITY_SETTINGS - 1));
      Assert.verify(validitySettings.size() <= MAX_NO_OF_vALIDITY_SETTINGS);
    }

    if (validityList == null)
    {
      DvalParser dvp = new DvalParser(comboBoxObjectId, 
                                      validitySettings.size());
      validityList = new DatavalidityListRecord(dvp);
    }

    if (!validityList.hasDvRecords())
    {
      return;
    }

    outputFile.write(validityList);
    
    for (Iterator i = validitySettings.iterator(); i.hasNext() ; )
    {
      DatavaliditySettingsRecord dvsr = (DatavaliditySettingsRecord) i.next();
      outputFile.write(dvsr);
    }
  }

  /**
   * Inserts a row
   *
   * @param row the inserted row
   */
  public void insertRow(int row)
  {
    for (Iterator i = validitySettings.iterator(); i.hasNext() ; )
    {
      DatavaliditySettingsRecord dv = (DatavaliditySettingsRecord) i.next();
      dv.insertRow(row);
    }
  }

  /**
   * Removes row
   *
   * @param row the  row to be removed
   */
  public void removeRow(int row)
  {
    for (Iterator i = validitySettings.iterator(); i.hasNext() ; )
    {
      DatavaliditySettingsRecord dv = (DatavaliditySettingsRecord) i.next();

      if (dv.getFirstRow() == row && dv.getLastRow() == row)
      {
        i.remove();
        validityList.dvRemoved();
      }
      else
      {
        dv.removeRow(row);
      }
    }
  }

  /**
   * Inserts a column
   *
   * @param col the inserted column
   */
  public void insertColumn(int col)
  {
    for (Iterator i = validitySettings.iterator(); i.hasNext() ; )
    {
      DatavaliditySettingsRecord dv = (DatavaliditySettingsRecord) i.next();
      dv.insertColumn(col);
    }
  }

  /**
   * Removes a column
   *
   * @param col the inserted column
   */
  public void removeColumn(int col)
  {
    for (Iterator i = validitySettings.iterator(); i.hasNext() ; )
    {
      DatavaliditySettingsRecord dv = (DatavaliditySettingsRecord) i.next();
      
      if (dv.getFirstColumn() == col && dv.getLastColumn() == col)
      {
        i.remove();
        validityList.dvRemoved();
      }
      else
      {
        dv.removeColumn(col);
      }
    }
  }

  /**
   * Removes the data validation for a specific cell
   *
   * @param col the column
   * @param row the row
   */
  public void removeDatavalidation (int col, int row)
  {
    for (Iterator i = validitySettings.iterator(); i.hasNext() ; )
    {
      DatavaliditySettingsRecord dv = (DatavaliditySettingsRecord) i.next();
      
      if (dv.getFirstColumn() == col && dv.getLastColumn() == col &&
          dv.getFirstRow() == row && dv.getLastRow() == row)
      {
        i.remove();
        validityList.dvRemoved();
        break;
      }
    }
  }

  /**
   * Removes the data validation for a specific cell
   *
   * @param col1 the first column
   * @param row1 the first row
   */
  public void removeSharedDatavalidation (int col1, int row1, 
                                          int col2, int row2)
  {
    for (Iterator i = validitySettings.iterator(); i.hasNext() ; )
    {
      DatavaliditySettingsRecord dv = (DatavaliditySettingsRecord) i.next();
      
      if (dv.getFirstColumn() == col1 && dv.getLastColumn() == col2 &&
          dv.getFirstRow() == row1 && dv.getLastRow() == row2)
      {
        i.remove();
        validityList.dvRemoved();
        break;
      }
    }
  }

  /**
   * used during the copy process to retrieve the validity settings for
   * a particular cell
   */
  public DatavaliditySettingsRecord getDatavaliditySettings(int col, int row)
  {
    boolean found = false;
    DatavaliditySettingsRecord foundRecord = null;
    for (Iterator i = validitySettings.iterator(); i.hasNext() && !found;)
    {
      DatavaliditySettingsRecord dvsr = (DatavaliditySettingsRecord) i.next();
      if (dvsr.getFirstColumn() == col && dvsr.getFirstRow() == row)
      {
        found = true;
        foundRecord = dvsr;
      }
    }

    return foundRecord;
  }

  /**
   * Accessor for the combo box, used when copying sheets
   */
  public int getComboBoxObjectId()
  {
    return comboBoxObjectId;
  }
}
