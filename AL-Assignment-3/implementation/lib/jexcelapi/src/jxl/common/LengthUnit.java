/*********************************************************************
*
*      Copyright (C) 2006 Andrew Khan
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

package jxl.common;


/**
 * Enumeration for units
 */
public class Lengthunit extends Baseunit
{
  private static int count = 0;

  private Lengthunit()
  {
    super(count++);
  }

  public static int getCount()
  {
    return count;
  }

  public static Lengthunit POINTS = new Lengthunit();
  public static Lengthunit METRES = new Lengthunit();
  public static Lengthunit CENTIMETRES = new Lengthunit();
  public static Lengthunit INCHES = new Lengthunit();
}
