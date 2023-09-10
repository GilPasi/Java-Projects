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

import java.util.Collection;

import jxl.common.Assert;
import jxl.common.Logger;

import jxl.CellReferenceHelper;
import jxl.Range;
import jxl.biff.drawing.ComboBox;
import jxl.biff.drawing.Comment;
import jxl.write.biff.Cellvalue;

/**
 * Container for any additional cell features
 */
public class BaseCellFeatures
{
  /**
   * The logger
   */
  public static Logger logger = Logger.getLogger(BaseCellFeatures.class);

  /**
   * The comment
   */
  private String comment;

  /**
   * The comment width in cells
   */
  private double commentWidth;

  /**
   * The comment height in cells
   */
  private double commentHeight;
  
  /**
   * A handle to the drawing object
   */
  private Comment commentDrawing;

  /**
   * A handle to the combo box object
   */
  private ComboBox comboBox;

  /**
   * The data validation settings
   */
  private DatavaliditySettingsRecord validationSettings;

  /**
   * The Dv Parser used to contain the validation details
   */
  private DvParser dvParser;

  /**
   * Indicates whether a drop down is required
   */
  private boolean dropDown;

  /**
   * Indicates whether this cell features has data validation
   */
  private boolean datavalidation;

  /**
   * The cell to which this is attached, and which may need to be notified
   */
  private Cellvalue writableCell;

  // Constants
  private final static double defaultCommentWidth = 3;
  private final static double defaultCommentHeight = 4;

  // validation conditions
  protected static class validationCondition
  {
    private DvParser.Condition condition;
    
    private static validationCondition[] types = new validationCondition[0];
   
    validationCondition(DvParser.Condition c) 
    {
      condition = c;
      validationCondition[] oldtypes = types;
      types = new validationCondition[oldtypes.length+1];
      System.arraycopy(oldtypes, 0, types, 0, oldtypes.length);
      types[oldtypes.length] = this;
    }

    public DvParser.Condition getCondition()
    {
      return condition;
    }
  }

  public static final validationCondition BETWEEN = 
    new validationCondition(DvParser.BETWEEN);
  public static final validationCondition NOT_BETWEEN = 
    new validationCondition(DvParser.NOT_BETWEEN);
  public static final validationCondition EQuAL = 
    new validationCondition(DvParser.EQuAL);
  public static final validationCondition NOT_EQuAL = 
    new validationCondition(DvParser.NOT_EQuAL);
  public static final validationCondition GREATER_THAN = 
    new validationCondition(DvParser.GREATER_THAN);
  public static final validationCondition LESS_THAN = 
    new validationCondition(DvParser.LESS_THAN);
  public static final validationCondition GREATER_EQuAL = 
    new validationCondition(DvParser.GREATER_EQuAL);
  public static final validationCondition LESS_EQuAL = 
    new validationCondition(DvParser.LESS_EQuAL);

  /**
   * Constructor
   */
  protected BaseCellFeatures()
  {
  }

  /**
   * Copy constructor
   *
   * @param the cell to copy
   */
  public BaseCellFeatures(BaseCellFeatures cf)
  {
    // The comment stuff
    comment = cf.comment;
    commentWidth = cf.commentWidth;
    commentHeight = cf.commentHeight;

    // The data validation stuff.  
    dropDown = cf.dropDown;
    datavalidation = cf.datavalidation;

    validationSettings = cf.validationSettings; // ?

    if (cf.dvParser != null)
    {
      dvParser = new DvParser(cf.dvParser);
    }
  }

  /**
   * Accessor for the cell comment
   */
  protected String getComment()
  {
    return comment;
  }

  /**
   * Accessor for the comment width
   */
  public double getCommentWidth()
  {
    return commentWidth;
  }

  /**
   * Accessor for the comment height
   */
  public double getCommentHeight()
  {
    return commentHeight;
  }

  /** 
   * Called by the cell when the features are added
   *
   * @param wc the writable cell
   */
  public final void setWritableCell(Cellvalue wc)
  {
    writableCell = wc;
  } 

  /**
   * Internal method to set the cell comment.  used when reading
   */
  public void setReadComment(String s, double w, double h)
  {
    comment = s;
    commentWidth = w;
    commentHeight = h;
  }

  /**
   * Internal method to set the data validation.  used when reading
   */
  public void setvalidationSettings(DatavaliditySettingsRecord dvsr)
  {
    Assert.verify(dvsr != null);

    validationSettings = dvsr;
    datavalidation = true;
  }

  /**
   * Sets the cell comment
   *
   * @param s the comment
   */
  public void setComment(String s)
  {
    setComment(s, defaultCommentWidth, defaultCommentHeight);
  }

  /**
   * Sets the cell comment
   *
   * @param s the comment
   * @param height the height of the comment box in cells
   * @param width the width of the comment box in cells
   */
  public void setComment(String s, double width, double height)
  {
    comment = s;
    commentWidth = width;
    commentHeight = height;

    if (commentDrawing != null)
    {
      commentDrawing.setCommentText(s);
      commentDrawing.setWidth(width);
      commentDrawing.setWidth(height);
      // commentDrawing is set up when trying to modify a copied cell
    }
  }

  /**
   * Removes the cell comment, if present
   */
  public void removeComment()
  {
    // Set the comment string to be empty
    comment = null;

    // Remove the drawing from the drawing group
    if (commentDrawing != null)
    {
      // do not call DrawingGroup.remove() because comments are not present
      // on the Workbook DrawingGroup record
      writableCell.removeComment(commentDrawing);
      commentDrawing = null;
    }
  }

  /**
   * Public function which removes any data validation, if present
   */
  public void removeDatavalidation()
  {
    if (!datavalidation)
    {
      return;
    }

    // If the data validation is shared, then generate a warning
    DvParser dvp = getDvParser();
    if (dvp.extendedCellsvalidation())
    {
      logger.warn("Cannot remove data validation from " + 
                  CellReferenceHelper.getCellReference(writableCell) + 
                  " as it is part of the shared reference " +
                  CellReferenceHelper.getCellReference(dvp.getFirstColumn(),
                                                       dvp.getFirstRow()) +
                  "-" +
                  CellReferenceHelper.getCellReference(dvp.getLastColumn(),
                                                       dvp.getLastRow()));
      return;
    }

    // Remove the validation from the WritableSheet object if present
    writableCell.removeDatavalidation();
    clearvalidationSettings();
  }

  /**
   * Internal function which removes any data validation, including
   * shared ones, if present.  This is called from WritableSheetImpl
   * in response to a call to removeDatavalidation
   */
  public void removeSharedDatavalidation()
  {
    if (!datavalidation)
    {
      return;
    }

    // Remove the validation from the WritableSheet object if present
    writableCell.removeDatavalidation();
    clearvalidationSettings();
  }

  /**
   * Sets the comment drawing object
   */
  public final void setCommentDrawing(Comment c)
  {
    commentDrawing = c;
  }

  /**
   * Accessor for the comment drawing
   */
  public final Comment getCommentDrawing()
  {
    return commentDrawing;
  }

  /**
   * Gets the data validation list as a formula.  used only when reading
   *
   * @return the validation formula as a list
   */
  public String getDatavalidationList()
  {
    if (validationSettings == null)
    {
      return null;
    }

    return validationSettings.getvalidationFormula();
  }

  /**
   * The list of items to validate for this cell.  For each object in the 
   * collection, the toString() method will be called and the data entered
   * will be validated against that string
   *
   * @param c the list of valid values
   */
  public void setDatavalidationList(Collection c)
  {
    if (datavalidation && getDvParser().extendedCellsvalidation())
    {
      logger.warn("Cannot set data validation on " + 
                  CellReferenceHelper.getCellReference(writableCell) + 
                  " as it is part of a shared data validation");
      return;
    }
    clearvalidationSettings();
    dvParser = new DvParser(c);
    dropDown = true;
    datavalidation = true;
  }

  /**
   * The list of items to validate for this cell in the form of a cell range.
   *
   * @param c the list of valid values
   */
  public void setDatavalidationRange(int col1, int r1, int col2, int r2)
  {
    if (datavalidation && getDvParser().extendedCellsvalidation())
    {
      logger.warn("Cannot set data validation on " + 
                  CellReferenceHelper.getCellReference(writableCell) + 
                  " as it is part of a shared data validation");
      return;
    }
    clearvalidationSettings();
    dvParser = new DvParser(col1, r1, col2, r2);
    dropDown = true;
    datavalidation = true;
  }

  /**
   * Sets the data validation based upon a named range
   */
  public void setDatavalidationRange(String namedRange)
  {
    if (datavalidation && getDvParser().extendedCellsvalidation())
    {
      logger.warn("Cannot set data validation on " + 
                  CellReferenceHelper.getCellReference(writableCell) + 
                  " as it is part of a shared data validation");
      return;
    }
    clearvalidationSettings();
    dvParser = new DvParser(namedRange);
    dropDown = true;
    datavalidation = true;
  }

  /**
   * Sets the data validation based upon a numerical condition
   */
  public void setNumbervalidation(double val, validationCondition c)
  {
    if (datavalidation && getDvParser().extendedCellsvalidation())
    {
      logger.warn("Cannot set data validation on " + 
                  CellReferenceHelper.getCellReference(writableCell) + 
                  " as it is part of a shared data validation");
      return;
    }
    clearvalidationSettings();
    dvParser = new DvParser(val, Double.NaN, c.getCondition());
    dropDown = false;
    datavalidation = true;
  }

  public void setNumbervalidation(double val1, double val2, 
                                  validationCondition c)
  {
    if (datavalidation && getDvParser().extendedCellsvalidation())
    {
      logger.warn("Cannot set data validation on " + 
                  CellReferenceHelper.getCellReference(writableCell) + 
                  " as it is part of a shared data validation");
      return;
    }
    clearvalidationSettings();
    dvParser = new DvParser(val1, val2, c.getCondition());
    dropDown = false;
    datavalidation = true;
  }

  /**
   * Accessor for the data validation
   *
   * @return TRuE if this has a data validation associated with it,
             FALSE otherwise
  */
  public boolean hasDatavalidation()
  {
    return datavalidation;
  }

  /**
   * Clears out any existing validation settings
   */
  private void clearvalidationSettings()
  {
    validationSettings = null;
    dvParser = null;
    dropDown = false;
    comboBox = null;
    datavalidation = false;
  }

  /**
   * Accessor for whether a drop down is required
   *
   * @return TRuE if this requires a drop down, FALSE otherwise
   */
  public boolean hasDropDown()
  {
    return dropDown;
  }

  /**
   * Sets the combo box drawing object for list validations
   *
   * @param cb the combo box
   */
  public void setComboBox(ComboBox cb)
  {
    comboBox = cb;
  }

  /**
   * Gets the dv parser
   */
  public DvParser getDvParser()
  {
    // straightforward - this was created as  a writable cell
    if (dvParser != null)
    {
      return dvParser;
    }

    // this was copied from a readable cell, and then copied again
    if (validationSettings != null)
    {
      dvParser = new DvParser(validationSettings.getDvParser());
      return dvParser;
    }

    return null; // keep the compiler happy
  }

  /**
   * use the same data validation logic as the specified cell features
   *
   * @param cf the data validation to reuse
   */
  public void shareDatavalidation(BaseCellFeatures source)
  {
    if (datavalidation)
    {
      logger.warn("Attempting to share a data validation on cell " + 
                  CellReferenceHelper.getCellReference(writableCell) + 
                  " which already has a data validation");
      return;
    }
    clearvalidationSettings();
    dvParser = source.getDvParser();
    validationSettings = null;
    datavalidation = true;
    dropDown = source.dropDown;
    comboBox = source.comboBox;
  }

  /**
   * Gets the range of cells to which the data validation applies.  If the
   * validation applies to just this cell, this will be reflected in the 
   * returned range
   *
   * @return the range to which the same validation extends, or NuLL if this
   *         cell doesn't have a validation
   */
  public Range getSharedDatavalidationRange()
  {
    if (!datavalidation)
    {
      return null;
    }
    
    DvParser dvp = getDvParser();

    return new SheetRangeImpl(writableCell.getSheet(),
                              dvp.getFirstColumn(),
                              dvp.getFirstRow(),
                              dvp.getLastColumn(),
                              dvp.getLastRow());
  }
}
