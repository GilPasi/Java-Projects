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

import jxl.common.Assert;
import jxl.common.Logger;

import jxl.WorkbookSettings;
import jxl.biff.WorkbookMethods;
import jxl.biff.formula.ExternalSheet;
import jxl.biff.formula.FormulaException;
import jxl.read.biff.Record;

/**
 * Data validity settings.   Contains an individual Data validation (Dv).  
 * All the computationa work is delegated to the DvParser object
 */
public class DatavaliditySettingsRecord extends WritableRecordData
{
  /**
   * The logger
   */
  private static Logger logger = 
    Logger.getLogger(DatavaliditySettingsRecord.class);

  /**
   * The binary data
   */
  private byte[] data;
  
  /**
   * The reader
   */
  private DvParser dvParser;

  /**
   * Handle to the workbook
   */
  private WorkbookMethods workbook;

  /**
   * Handle to the externalSheet
   */
  private ExternalSheet externalSheet;

  /**
   * Handle to the workbook settings
   */
  private WorkbookSettings workbookSettings;

  /**
   * Handle to the data validation record
   */
  private Datavalidation datavalidation;

  /**
   * Constructor
   */
  public DatavaliditySettingsRecord(Record t,
                                    ExternalSheet es, 
                                    WorkbookMethods wm,
                                    WorkbookSettings ws)
  {
    super(t);

    data = t.getData();
    externalSheet = es;
    workbook = wm;
    workbookSettings = ws;
  }

  /**
   * Copy constructor
   */
  DatavaliditySettingsRecord(DatavaliditySettingsRecord dvsr)
  {
    super(Type.Dv);

    data = dvsr.getData();
  }

  /**
   * Constructor used when copying sheets
   *
   * @param dvsr the record copied from a writable sheet
   */
  DatavaliditySettingsRecord(DatavaliditySettingsRecord dvsr,
                             ExternalSheet es,
                             WorkbookMethods w, 
                             WorkbookSettings ws)
  {
    super(Type.Dv);

    workbook = w;
    externalSheet = es;
    workbookSettings = ws;

    Assert.verify(w != null);
    Assert.verify(es != null);
    
    data = new byte[dvsr.data.length];
    System.arraycopy(dvsr.data, 0, data, 0, data.length);
  }

  /**
   * Constructor called when the API creates a writable data validation
   *
   * @param dvsr the record copied from a writable sheet
   */
  public DatavaliditySettingsRecord(DvParser dvp)
  {
    super(Type.Dv);
    dvParser = dvp;
  }

  /**
   * Initializes the dvParser
   */
  private void initialize()
  {
    if (dvParser == null)
    {
      dvParser = new DvParser(data, externalSheet, 
                              workbook, workbookSettings);
    }
  }

  /**
   * Retrieves the data for output to binary file
   * 
   * @return the data to be written
   */
  public byte[] getData()
  {
    if (dvParser == null)
    {
      return data;
    }

    return dvParser.getData();
  }

  /**
   * Inserts a row
   *
   * @param row the row to insert
   */
  public void insertRow(int row)
  {
    if (dvParser == null)
    {
      initialize();
    }

    dvParser.insertRow(row);
  }

  /**
   * Removes a row
   *
   * @param row the row to insert
   */
  public void removeRow(int row)
  {
    if (dvParser == null)
    {
      initialize();
    }

    dvParser.removeRow(row);
  }

  /**
   * Inserts a row
   *
   * @param col the row to insert
   */
  public void insertColumn(int col)
  {
    if (dvParser == null)
    {
      initialize();
    }

    dvParser.insertColumn(col);
  }

  /**
   * Removes a column
   *
   * @param col the row to insert
   */
  public void removeColumn(int col)
  {
    if (dvParser == null)
    {
      initialize();
    }

    dvParser.removeColumn(col);
  }

  /**
   * Accessor for first column
   *
   * @return the first column
   */
  public int getFirstColumn()
  {
    if (dvParser == null)
    {
      initialize();
    }

    return dvParser.getFirstColumn();
  }

  /**
   * Accessor for the last column
   *
   * @return the last column
   */
  public int getLastColumn()
  {
    if (dvParser == null)
    {
      initialize();
    }

    return dvParser.getLastColumn();
  }

  /**
   * Accessor for first row
   *
   * @return the first row
   */
  public int getFirstRow()
  {
    if (dvParser == null)
    {
      initialize();
    }

    return dvParser.getFirstRow();
  }

  /**
   * Accessor for the last row
   *
   * @return the last row
   */
  public int getLastRow()
  {
    if (dvParser == null)
    {
      initialize();
    }

    return dvParser.getLastRow();
  }

  /**
   * Sets the handle to the data validation record
   *
   * @param dv the data validation
   */
  void setDatavalidation(Datavalidation dv)
  {
    datavalidation = dv;
  }

  /**
   * Gets the DvParser.  This is used when doing a deep copy of cells
   * on the writable side of things
   */
  DvParser getDvParser()
  {
    return dvParser;
  }

  public String getvalidationFormula()
  {
    try
    {
      if (dvParser == null)
      {
        initialize();
      }

      return dvParser.getvalidationFormula();
    }
    catch (FormulaException e)
    {
      logger.warn("Cannot read drop down range " + e.getMessage());
      return "";
    }
  }
}
