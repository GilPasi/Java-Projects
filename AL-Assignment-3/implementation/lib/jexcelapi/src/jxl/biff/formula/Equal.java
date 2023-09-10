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

package jxl.biff.formula;

/**
 * A cell reference in a formula
 */
class Equal extends BinaryOperator implements ParsedThing
{
  /**
   * Constructor
   */
  public Equal()
  {
  }

  /**
   * Gets the string for this symbol
   *
   * @return the symbol string
   */
  public String getSymbol()
  {
    return "=";
  }


  /**
   * Abstract method which gets the token for this operator
   *
   * @return the string symbol for this token
   */
  Token getToken()
  {
    return Token.EQuAL;
  }

  /**
   * Gets the precedence for this operator.  Operator precedents run from
   * 1 to 5, one being the highest, 5 being the lowest
   *
   * @return the operator precedence
   */
  int getPrecedence()
  {
    return 5;
  }

}
