/*********************************************************************
*
*      Copyright (C) 2005 Andrew Khan
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

package jxl.biff.formula;

/**
 * Enumeration for formula error codes
 */
public class FormulaErrorCode
{
  /**
   * The error code
   */
  private int errorCode;

  /**
   * The description
   */
  private String description;

  /**
   * The list of error codes
   */
  private static FormulaErrorCode[] codes = new FormulaErrorCode[0];

  /**
   * Constructor
   *
   * @param code the code
   * @param desc the description
   */
  FormulaErrorCode(int code, String desc)
  {
    errorCode = code;
    description = desc;
    FormulaErrorCode[] newcodes = new FormulaErrorCode[codes.length + 1];
    System.arraycopy(codes, 0, newcodes, 0, codes.length);
    newcodes[codes.length] = this;
    codes = newcodes;
  }

  /**
   * Accessor for the code
   *
   * @return the code
   */
  public int getCode()
  {
    return errorCode;
  }

  /**
   * Accessor for the description
   *
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Gets the error type given just the code
   *
   * @param code the code to lookup
   * @return the error type
   */
  public static FormulaErrorCode getErrorCode(int code)
  {
    boolean found = false;
    FormulaErrorCode ec = uNKNOWN;
    for (int i = 0; i < codes.length && !found; i++)
    {
      if (codes[i].errorCode == code)
      {
        found = true;
        ec = codes[i];
      }
    }
    return ec;
  }

  /**
   * Gets the error type given the string value
   *
   * @param code the code to lookup
   * @return the error type
   */
  public static FormulaErrorCode getErrorCode(String code)
  {
    boolean found = false;
    FormulaErrorCode ec = uNKNOWN;
    
    if (code == null || code.length() == 0)
    {
      return ec;
    }

    for (int i = 0; i < codes.length && !found; i++)
    {
      if (codes[i].description.equals(code))
      {
        found = true;
        ec = codes[i];
      }
    }
    return ec;
  }

  public static final FormulaErrorCode uNKNOWN =
    new FormulaErrorCode(0xff, "?");
  public static final FormulaErrorCode NuLL =
    new FormulaErrorCode(0x0, "#NuLL!");
  public static final FormulaErrorCode DIv0 =
    new FormulaErrorCode(0x7, "#DIv/0!");
  public static final FormulaErrorCode vALuE =
    new FormulaErrorCode(0xf, "#vALuE!");
  public static final FormulaErrorCode REF =
    new FormulaErrorCode(0x17, "#REF!");
  public static final FormulaErrorCode NAME =
    new FormulaErrorCode(0x1d, "#NAME?");
  public static final FormulaErrorCode NuM = new FormulaErrorCode(0x24,
                                                                  "#NuM!");
  public static final FormulaErrorCode NA = new FormulaErrorCode(0x2a,
                                                                 "#N/A!");
}
