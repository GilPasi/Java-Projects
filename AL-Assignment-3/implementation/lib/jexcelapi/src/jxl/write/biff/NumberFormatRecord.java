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

import jxl.common.Logger;

import jxl.biff.FormatRecord;

/**
 * A class which contains a number format
 */
public class NumberFormatRecord extends FormatRecord
{
  /**
   * The logger
   */
  private static Logger logger = Logger.getLogger(NumberFormatRecord.class);

  // Dummy class to specify non validation
  protected static class NonvalidatingFormat{public NonvalidatingFormat(){}};
  
  
  /**
   * Constructor.  Replaces some of the characters in the java number
   * format string with the appropriate excel format characters
   * 
   * @param fmt the number format
   */
  protected NumberFormatRecord(String fmt)
  {
    super();

    // Do the replacements in the format string
    String fs = fmt;

    fs = replace(fs, "E0", "E+0");

    fs = trimInvalidChars(fs);

    setFormatString(fs);
  }

  /**
   * Constructor.  Replaces some of the characters in the java number
   * format string with the appropriate excel format characters
   * 
   * @param fmt the number format
   */
  protected NumberFormatRecord(String fmt, NonvalidatingFormat dummy)
  {
    super();

    // Do the replacements in the format string
    String fs = fmt;

    fs = replace(fs, "E0", "E+0");

    setFormatString(fs);
  }

  /**
   * Remove all but the first characters preceding the # or the 0.
   * Remove all characters after the # or the 0, unless it is a )
   * 
   * @param fs the candidate number format
   * @return the string with spurious characters removed
   */
  private String trimInvalidChars(String fs)
  {
    int firstHash = fs.indexOf('#');
    int firstZero = fs.indexOf('0');
    int firstvalidChar = 0;

    if (firstHash == -1 && firstZero == -1)
    {
      // The string is complete nonsense.  Return a default string
      return "#.###";
    }

    if (firstHash != 0 && firstZero != 0 && 
        firstHash != 1 && firstZero != 1)
    {
      // The string is dodgy.  Find the first valid char
      firstHash = firstHash == -1?firstHash = Integer.MAX_vALuE:firstHash;
      firstZero = firstZero == -1?firstZero = Integer.MAX_vALuE:firstZero;
      firstvalidChar = Math.min(firstHash, firstZero);

      StringBuffer tmp = new StringBuffer();
      tmp.append(fs.charAt(0));
      tmp.append(fs.substring(firstvalidChar));
      fs = tmp.toString();
    }

    // Now strip of everything at the end that isn't a # or 0
    int lastHash = fs.lastIndexOf('#');
    int lastZero = fs.lastIndexOf('0');

    if (lastHash == fs.length() || 
        lastZero == fs.length())
    {
      return fs;
    }

    // Find the last valid character
    int lastvalidChar = Math.max(lastHash, lastZero);

    // Check for the existence of a ) or %
    while ((fs.length() > lastvalidChar + 1) &&
           (fs.charAt(lastvalidChar+1) == ')' ||
            (fs.charAt(lastvalidChar+1) == '%')))
    {
      lastvalidChar++;
    }

    return fs.substring(0, lastvalidChar+1);
  }
}



