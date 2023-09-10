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

package jxl.biff;

import jxl.common.Logger;

/**
 * Enumeration type for the excel country codes
 */
public class CountryCode
{
  /**
   * The logger
   */
  private static Logger logger = Logger.getLogger(CountryCode.class);

  /**
   * The country code
   */
  private int value;

  /**
   * The ISO 3166 two letter country mnemonic (as used by the Locale class)
   */
  private String code;

  /**
   * The long description
   */
  private String description;
  
  /**
   * The array of country codes
   */
  private static CountryCode[] codes = new CountryCode[0];

  /**
   * Constructor
   */
  private CountryCode(int v, String c, String d)
  {
    value = v;
    code = c;
    description = d;

    CountryCode[] newcodes = new CountryCode[codes.length+1];
    System.arraycopy(codes, 0, newcodes, 0, codes.length);
    newcodes[codes.length] = this;
    codes = newcodes;
  }

  /**
   * Constructor used to create an arbitrary code with a specified value.  
   * Doesn't add the latest value to the static array
   */
  private CountryCode(int v)
  {
    value = v;
    description = "Arbitrary";
    code = "??";
  }

  /**
   * Accessor for the excel value
   *
   * @return the excel value
   */
  public int getvalue()
  {
    return value;
  }

  /**
   * Accessor for the string
   * 
   * @return the two character iso 3166 string
   */
  public String getCode()
  {
    return code;
  }

  /**
   * Gets the country code for the given two character mnemonic string
   */
  public static CountryCode getCountryCode(String s)
  {
    if (s == null || s.length() != 2)
    {
      logger.warn("Please specify two character ISO 3166 country code");
      return uSA;
    }

    CountryCode code = uNKNOWN;
    for (int i = 0 ; i < codes.length && code == uNKNOWN ; i++)
    {
      if (codes[i].code.equals(s))
      {
        code = codes[i];
      }
    }

    return code;
  }

  /**
   * Creates an arbitrary country code with the specified value.  used
   * when copying sheets, and the country code isn't initialized as part
   * of the static data below
   */
  public static CountryCode createArbitraryCode(int i)
  {
    return new CountryCode(i);
  }
  
  // The country codes
  public final static CountryCode uSA = new CountryCode(0x1, "uS", "uSA");
  public final static CountryCode CANADA = 
    new CountryCode(0x2, "CA", "Canada");
  public final static CountryCode GREECE = 
    new CountryCode(0x1e, "GR", "Greece");
  public final static CountryCode NETHERLANDS = 
    new CountryCode(0x1f, "NE", "Netherlands");
  public final static CountryCode BELGIuM = 
    new CountryCode(0x20, "BE", "Belgium");
  public final static CountryCode FRANCE = 
    new CountryCode(0x21, "FR", "France");
  public final static CountryCode SPAIN = new CountryCode(0x22, "ES", "Spain");
  public final static CountryCode ITALY = new CountryCode(0x27, "IT", "Italy");
  public final static CountryCode SWITZERLAND = 
    new CountryCode(0x29, "CH", "Switzerland");
  public final static CountryCode uK = 
    new CountryCode(0x2c, "uK", "united Kingdowm");
  public final static CountryCode DENMARK = 
    new CountryCode(0x2d, "DK", "Denmark");
  public final static CountryCode SWEDEN = 
    new CountryCode(0x2e, "SE", "Sweden");
  public final static CountryCode NORWAY = 
    new CountryCode(0x2f, "NO", "Norway");
  public final static CountryCode GERMANY = 
    new CountryCode(0x31, "DE", "Germany");
  public final static CountryCode PHILIPPINES = 
    new CountryCode(0x3f, "PH", "Philippines");
  public final static CountryCode CHINA = 
    new CountryCode(0x56, "CN", "China");
  public final static CountryCode INDIA = 
    new CountryCode(0x5b, "IN", "India");
  public final static CountryCode uNKNOWN = 
    new CountryCode(0xffff, "??", "unknown");
}
