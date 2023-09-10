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
 * Enumeration class which contains the various colours available within
 * the standard Excel colour palette
 * 
 */
public /*final*/ class Colour
{
  /**
   * The internal numerical representation of the colour
   */
  private int value;

  /** 
   * The default RGB value
   */
  private RGB rgb;

  /**
   * The display string for the colour.  used when presenting the 
   * format information
   */
  private String string;

  /**
   * The list of internal colours
   */
  private static Colour[] colours  = new Colour[0];

  /**
   * Private constructor
   * 
   * @param val 
   * @param s the display string
   * @param r the default red value
   * @param g the default green value
   * @param b the default blue value
   */
  protected Colour(int val, String s, int r, int g, int b)
  {
    value = val;
    string = s;
    rgb = new RGB(r,g,b);

    Colour[] oldcols = colours;
    colours = new Colour[oldcols.length + 1];
    System.arraycopy(oldcols, 0, colours, 0, oldcols.length);
    colours[oldcols.length] = this;
  }

  /**
   * Gets the value of this colour.  This is the value that is written to 
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
   * Gets the default red content of this colour.  used when writing the
   * default colour palette
   *
   * @return the red content of this colour
   * @deprecated use getDefaultRGB instead
   */
  public int getDefaultRed()
  {
    return rgb.getRed();
  }

  /**
   * Gets the default green content of this colour.  used when writing the
   * default colour palette
   *
   * @return the green content of this colour
   * @deprecated use getDefaultRGB instead
   */
  public int getDefaultGreen()
  {
    return rgb.getGreen();
  }

  /**
   * Gets the default blue content of this colour.  used when writing the
   * default colour palette
   *
   * @return the blue content of this colour
   * @deprecated use getDefaultRGB instead
   */
  public int getDefaultBlue()
  {
    return rgb.getBlue();
  }

  /**
   * Returns the default RGB of the colour
   *
   * @return the default RGB
   */
  public RGB getDefaultRGB()
  {
    return rgb;
  }

  /**
   * Gets the internal colour from the value
   *
   * @param val 
   * @return the colour with that value
   */
  public static Colour getInternalColour(int val)
  {
    for (int i = 0 ; i < colours.length ; i++)
    {
      if (colours[i].getvalue() == val)
      {
        return colours[i];
      }
    }

    return uNKNOWN;
  }

  /** 
   * Returns all available colours - used when generating the default palette
   *
   * @return all available colours
   */
  public static Colour[] getAllColours()
  {
    return colours;
  }

  // Major colours
  public final static Colour uNKNOWN      = 
    new Colour(0x7fee, "unknown", 0, 0, 0);
  public final static Colour BLACK        =
    new Colour(0x7fff, "black", 0, 0, 0);
  public final static Colour WHITE        = 
    new Colour(0x9, "white", 0xff, 0xff, 0xff);
  public final static Colour DEFAuLT_BACKGROuND1
    = new Colour(0x0, "default background", 0xff, 0xff, 0xff);
  public final static Colour DEFAuLT_BACKGROuND
    = new Colour(0xc0, "default background", 0xff, 0xff, 0xff);
  public final static Colour PALETTE_BLACK = 
    new Colour(0x8, "black", 0x1, 0, 0);
                               // the first item in the colour palette

  // Other colours
  public final static Colour RED             = new Colour(0xa, "red",0xff,0,0);
  public final static Colour BRIGHT_GREEN    = new Colour(0xb, "bright green",0,0xff,0);
  public final static Colour BLuE            = new Colour(0xc, "blue",0,0,0xff);  public final static Colour YELLOW          = new Colour(0xd, "yellow",0xff,0xff,0);
  public final static Colour PINK            = new Colour(0xe, "pink",0xff,0,0xff);
  public final static Colour TuRQuOISE       = new Colour(0xf, "turquoise",0,0xff,0xff);
  public final static Colour DARK_RED        = new Colour(0x10, "dark red",0x80,0,0);
  public final static Colour GREEN           = new Colour(0x11, "green",0,0x80,0);
  public final static Colour DARK_BLuE       = new Colour(0x12, "dark blue",0,0,0x80);
  public final static Colour DARK_YELLOW     = new Colour(0x13, "dark yellow",0x80,0x80,0);
  public final static Colour vIOLET          = new Colour(0x14, "violet",0x80,0x80,0);
  public final static Colour TEAL            = new Colour(0x15, "teal",0,0x80,0x80);
  public final static Colour GREY_25_PERCENT = new Colour(0x16 ,"grey 25%",0xc0,0xc0,0xc0);
  public final static Colour GREY_50_PERCENT = new Colour(0x17, "grey 50%",0x80,0x80,0x80);
  public final static Colour PERIWINKLE      = new Colour(0x18, "periwinkle%",0x99, 0x99, 0xff);
  public final static Colour PLuu           = new Colour(0x19, "plum",0x99, 0x33, 0x66);
  public final static Colour IvORY           = new Colour(0x1a, "ivory",0xff, 0xff, 0xcc);
  public final static Colour LIGHT_TuRQuOISE2= new Colour(0x1b, "light turquoise",0xcc, 0xff, 0xff);
  public final static Colour DARK_PuRPLE     = new Colour(0x1c, "dark purple",0x66, 0x0, 0x66);
  public final static Colour CORAL           = new Colour(0x1d, "coral",0xff, 0x80, 0x80);
  public final static Colour OCEAN_BLuE      = new Colour(0x1e, "ocean blue",0x0, 0x66, 0xcc);
  public final static Colour ICE_BLuE        = new Colour(0x1f, "ice blue",0xcc, 0xcc, 0xff);
  public final static Colour DARK_BLuE2      = new Colour(0x20, "dark blue",0,0,0x80);
  public final static Colour PINK2           = new Colour(0x21, "pink",0xff,0,0xff);
  public final static Colour YELLOW2         = new Colour(0x22, "yellow",0xff,0xff,0x0);
  public final static Colour TuRQOISE2       = new Colour(0x23, "turqoise",0x0,0xff,0xff);
  public final static Colour vIOLET2         = new Colour(0x24, "violet",0x80,0x0,0x80);
  public final static Colour DARK_RED2       = new Colour(0x25, "dark red",0x80,0x0,0x0);
  public final static Colour TEAL2           = new Colour(0x26, "teal",0x0,0x80,0x80);
  public final static Colour BLuE2           = new Colour(0x27, "blue",0x0,0x0,0xff);
  public final static Colour SKY_BLuE        = new Colour(0x28, "sky blue",0,0xcc,0xff);
  public final static Colour LIGHT_TuRQuOISE 
      = new Colour(0x29, "light turquoise",0xcc,0xff,0xff);
  public final static Colour LIGHT_GREEN     = new Colour(0x2a, "light green",0xcc,0xff,0xcc);
  public final static Colour vERY_LIGHT_YELLOW       
    = new Colour(0x2b, "very light yellow",0xff,0xff,0x99);
  public final static Colour PALE_BLuE       = new Colour(0x2c, "pale blue",0x99,0xcc,0xff);
  public final static Colour ROSE            = new Colour(0x2d, "rose",0xff,0x99,0xcc);
  public final static Colour LAvENDER        = new Colour(0x2e, "lavender",0xcc,0x99,0xff);
  public final static Colour TAN             = new Colour(0x2f, "tan",0xff,0xcc,0x99);
  public final static Colour LIGHT_BLuE      = new Colour(0x30, "light blue", 0x33, 0x66, 0xff);
  public final static Colour AQuA            = new Colour(0x31, "aqua",0x33,0xcc,0xcc);
  public final static Colour LIME            = new Colour(0x32, "lime",0x99,0xcc,0);
  public final static Colour GOLD            = new Colour(0x33, "gold",0xff,0xcc,0);
  public final static Colour LIGHT_ORANGE    
      = new Colour(0x34, "light orange",0xff,0x99,0);
  public final static Colour ORANGE          = new Colour(0x35, "orange",0xff,0x66,0);
  public final static Colour BLuE_GREY       = new Colour(0x36, "blue grey",0x66,0x66,0xcc);
  public final static Colour GREY_40_PERCENT = new Colour(0x37, "grey 40%",0x96,0x96,0x96);
  public final static Colour DARK_TEAL       = new Colour(0x38, "dark teal",0,0x33,0x66);
  public final static Colour SEA_GREEN       = new Colour(0x39, "sea green",0x33,0x99,0x66);
  public final static Colour DARK_GREEN      = new Colour(0x3a, "dark green",0,0x33,0);
  public final static Colour OLIvE_GREEN     = new Colour(0x3b, "olive green",0x33,0x33,0);
  public final static Colour BROWN           = new Colour(0x3c, "brown",0x99,0x33,0);
  public final static Colour PLuM            = new Colour(0x3d, "plum",0x99,0x33,0x66);
  public final static Colour INDIGO          = new Colour(0x3e, "indigo",0x33,0x33,0x99);
  public final static Colour GREY_80_PERCENT = new Colour(0x3f, "grey 80%",0x33,0x33,0x33);
  public final static Colour AuTOMATIC = new Colour(0x40, "automatic", 0xff, 0xff, 0xff);

  // Colours added for backwards compatibility
  public final static Colour GRAY_80 = GREY_80_PERCENT;
  public final static Colour GRAY_50 = GREY_50_PERCENT;
  public final static Colour GRAY_25 = GREY_25_PERCENT;
}











