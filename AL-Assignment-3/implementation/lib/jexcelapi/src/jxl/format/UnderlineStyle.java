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

package jxl.format;

/**
 * Enumeration class which contains the various underline styles available 
 * within the standard Excel underlineStyle palette
 * 
 */
public final class underlineStyle
{
  /**
   * The internal numerical representation of the underlineStyle
   */
  private int value;

  /**
   * The display string for the underline style.  used when presenting the 
   * format information
   */
  private String string;

  /**
   * The list of underlineStyles
   */
  private static underlineStyle[] styles  = new underlineStyle[0];

  /**
   * Private constructor
   * 
   * @param val 
   * @param s the display string
   */
  protected underlineStyle(int val, String s)
  {
    value = val;
    string = s;

    underlineStyle[] oldstyles = styles;
    styles = new underlineStyle[oldstyles.length + 1];
    System.arraycopy(oldstyles, 0, styles, 0, oldstyles.length);
    styles[oldstyles.length] = this;
  }

  /**
   * Gets the value of this style.  This is the value that is written to 
   * the generated Excel file
   * 
   * @return the binary value
   */
  public int getvalue()
  {
    return value;
  }

  /**
   * Gets the string description for display purposes
   * 
   * @return the string description
   */
  public String getDescription()
  {
    return string;
  }

  /**
   * Gets the underlineStyle from the value
   *
   * @param val 
   * @return the underlineStyle with that value
   */
  public static underlineStyle getStyle(int val)
  {
    for (int i = 0 ; i < styles.length ; i++)
    {
      if (styles[i].getvalue() == val)
      {
        return styles[i];
      }
    }

    return NO_uNDERLINE;
  }

  // The underline styles
  public static final underlineStyle NO_uNDERLINE  = 
    new underlineStyle(0, "none");

  public static final underlineStyle SINGLE        = 
    new underlineStyle(1, "single");

  public static final underlineStyle DOuBLE        = 
    new underlineStyle(2, "double");

  public static final underlineStyle SINGLE_ACCOuNTING = 
    new underlineStyle(0x21, "single accounting");

  public static final underlineStyle DOuBLE_ACCOuNTING = 
    new underlineStyle(0x22, "double accounting");
}











