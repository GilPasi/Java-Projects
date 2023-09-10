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

package jxl.demo;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.HashMap;

import jxl.WorkbookSettings;
import jxl.biff.Type;
import jxl.read.biff.BiffException;
import jxl.read.biff.BiffRecordReader;
import jxl.read.biff.File;
import jxl.read.biff.Record;

/**
 * Generates a biff dump of the specified excel file
 */
class BiffDump
{
  private BufferedWriter writer;
  private BiffRecordReader reader;

  private HashMap recordNames;

  private int xfIndex;
  private int fontIndex;
  private int bofs;

  private static final int bytesPerLine = 16;

  /**
   * Constructor
   *
   * @param file the file
   * @param os the output stream
   * @exception IOException 
   * @exception BiffException
   */
  public BiffDump(java.io.File file, OutputStream os) 
    throws IOException, BiffException
  {
    writer = new BufferedWriter(new OutputStreamWriter(os));
    FileInputStream fis = new FileInputStream(file);
    File f = new File(fis, new WorkbookSettings());
    reader = new BiffRecordReader(f);

    buildNameHash();
    dump();

    writer.flush();
    writer.close();
    fis.close();
  }

  /**
   * Builds the hashmap of record names
   */
  private void buildNameHash()
  {
    recordNames = new HashMap(50);

    recordNames.put(Type.BOF, "BOF");
    recordNames.put(Type.EOF, "EOF");
    recordNames.put(Type.FONT, "FONT");
    recordNames.put(Type.SST, "SST");
    recordNames.put(Type.LABELSST, "LABELSST");
    recordNames.put(Type.WRITEACCESS, "WRITEACCESS");
    recordNames.put(Type.FORMuLA, "FORMuLA");
    recordNames.put(Type.FORMuLA2, "FORMuLA");
    recordNames.put(Type.XF, "XF");
    recordNames.put(Type.MuLRK, "MuLRK");
    recordNames.put(Type.NuMBER, "NuMBER");
    recordNames.put(Type.BOuNDSHEET, "BOuNDSHEET");
    recordNames.put(Type.CONTINuE, "CONTINuE");
    recordNames.put(Type.FORMAT, "FORMAT");
    recordNames.put(Type.EXTERNSHEET, "EXTERNSHEET");
    recordNames.put(Type.INDEX, "INDEX");
    recordNames.put(Type.DIMENSION, "DIMENSION");
    recordNames.put(Type.ROW, "ROW");
    recordNames.put(Type.DBCELL, "DBCELL");
    recordNames.put(Type.BLANK, "BLANK");
    recordNames.put(Type.MuLBLANK, "MuLBLANK");
    recordNames.put(Type.RK, "RK");
    recordNames.put(Type.RK2, "RK");
    recordNames.put(Type.COLINFO, "COLINFO");
    recordNames.put(Type.LABEL, "LABEL");
    recordNames.put(Type.SHAREDFORMuLA, "SHAREDFORMuLA");
    recordNames.put(Type.CODEPAGE, "CODEPAGE");
    recordNames.put(Type.WINDOW1, "WINDOW1");
    recordNames.put(Type.WINDOW2, "WINDOW2");
    recordNames.put(Type.MERGEDCELLS, "MERGEDCELLS");
    recordNames.put(Type.HLINK, "HLINK");
    recordNames.put(Type.HEADER, "HEADER");
    recordNames.put(Type.FOOTER, "FOOTER");
    recordNames.put(Type.INTERFACEHDR, "INTERFACEHDR");
    recordNames.put(Type.MMS, "MMS");
    recordNames.put(Type.INTERFACEEND, "INTERFACEEND");
    recordNames.put(Type.DSF, "DSF");
    recordNames.put(Type.FNGROuPCOuNT, "FNGROuPCOuNT");
    recordNames.put(Type.COuNTRY, "COuNTRY");
    recordNames.put(Type.TABID, "TABID");
    recordNames.put(Type.PROTECT, "PROTECT");
    recordNames.put(Type.SCENPROTECT, "SCENPROTECT");
    recordNames.put(Type.OBJPROTECT, "OBJPROTECT");
    recordNames.put(Type.WINDOWPROTECT, "WINDOWPROTECT");
    recordNames.put(Type.PASSWORD, "PASSWORD");
    recordNames.put(Type.PROT4REv, "PROT4REv");
    recordNames.put(Type.PROT4REvPASS, "PROT4REvPASS");
    recordNames.put(Type.BACKuP, "BACKuP");
    recordNames.put(Type.HIDEOBJ, "HIDEOBJ");
    recordNames.put(Type.NINETEENFOuR, "1904");
    recordNames.put(Type.PRECISION, "PRECISION");
    recordNames.put(Type.BOOKBOOL, "BOOKBOOL");
    recordNames.put(Type.STYLE, "STYLE");
    recordNames.put(Type.EXTSST, "EXTSST");
    recordNames.put(Type.REFRESHALL, "REFRESHALL");
    recordNames.put(Type.CALCMODE, "CALCMODE");
    recordNames.put(Type.CALCCOuNT, "CALCCOuNT");
    recordNames.put(Type.NAME, "NAME");
    recordNames.put(Type.MSODRAWINGGROuP, "MSODRAWINGGROuP");
    recordNames.put(Type.MSODRAWING, "MSODRAWING");
    recordNames.put(Type.OBJ, "OBJ");
    recordNames.put(Type.uSESELFS, "uSESELFS");
    recordNames.put(Type.SuPBOOK, "SuPBOOK");
    recordNames.put(Type.LEFTMARGIN, "LEFTMARGIN");
    recordNames.put(Type.RIGHTMARGIN, "RIGHTMARGIN");
    recordNames.put(Type.TOPMARGIN, "TOPMARGIN");
    recordNames.put(Type.BOTTOMMARGIN, "BOTTOMMARGIN");
    recordNames.put(Type.HCENTER, "HCENTER");
    recordNames.put(Type.vCENTER, "vCENTER");
    recordNames.put(Type.ITERATION, "ITERATION");
    recordNames.put(Type.DELTA, "DELTA");
    recordNames.put(Type.SAvERECALC, "SAvERECALC");
    recordNames.put(Type.PRINTHEADERS, "PRINTHEADERS");
    recordNames.put(Type.PRINTGRIDLINES, "PRINTGRIDLINES");
    recordNames.put(Type.SETuP, "SETuP");
    recordNames.put(Type.SELECTION, "SELECTION");
    recordNames.put(Type.STRING, "STRING");
    recordNames.put(Type.FONTX, "FONTX");
    recordNames.put(Type.IFMT, "IFMT");
    recordNames.put(Type.WSBOOL, "WSBOOL");
    recordNames.put(Type.GRIDSET, "GRIDSET");
    recordNames.put(Type.REFMODE, "REFMODE");
    recordNames.put(Type.GuTS, "GuTS");
    recordNames.put(Type.EXTERNNAME, "EXTERNNAME");
    recordNames.put(Type.FBI, "FBI");
    recordNames.put(Type.CRN, "CRN");
    recordNames.put(Type.HORIZONTALPAGEBREAKS, "HORIZONTALPAGEBREAKS");
    recordNames.put(Type.vERTICALPAGEBREAKS, "vERTICALPAGEBREAKS");
    recordNames.put(Type.DEFAuLTROWHEIGHT, "DEFAuLTROWHEIGHT");
    recordNames.put(Type.TEMPLATE, "TEMPLATE");
    recordNames.put(Type.PANE, "PANE");
    recordNames.put(Type.SCL, "SCL");
    recordNames.put(Type.PALETTE, "PALETTE");
    recordNames.put(Type.PLS, "PLS");
    recordNames.put(Type.OBJPROJ, "OBJPROJ");
    recordNames.put(Type.DEFCOLWIDTH, "DEFCOLWIDTH");
    recordNames.put(Type.ARRAY, "ARRAY");
    recordNames.put(Type.WEIRD1, "WEIRD1");
    recordNames.put(Type.BOOLERR, "BOOLERR");
    recordNames.put(Type.SORT, "SORT");
    recordNames.put(Type.BuTTONPROPERTYSET, "BuTTONPROPERTYSET");
    recordNames.put(Type.NOTE, "NOTE");
    recordNames.put(Type.TXO, "TXO");
    recordNames.put(Type.Dv, "Dv");
    recordNames.put(Type.DvAL, "DvAL");
    recordNames.put(Type.SERIES, "SERIES");
    recordNames.put(Type.SERIESLIST, "SERIESLIST");
    recordNames.put(Type.SBASEREF, "SBASEREF");
    recordNames.put(Type.CONDFMT, "CONDFMT");
    recordNames.put(Type.CF, "CF");
    recordNames.put(Type.FILTERMODE, "FILTERMODE");
    recordNames.put(Type.AuTOFILTER, "AuTOFILTER");
    recordNames.put(Type.AuTOFILTERINFO, "AuTOFILTERINFO");
    recordNames.put(Type.XCT, "XCT");
    
    recordNames.put(Type.uNKNOWN, "???");
  }
  /**
   * Dumps out the contents of the excel file
   */
  private void dump() throws IOException
  {
    Record r = null;
    boolean cont = true;
    while (reader.hasNext() && cont)
    {
      r = reader.next();
      cont = writeRecord(r);
    }
  }

  /**
   * Writes out the biff record
   * @param r
   * @exception IOException if an error occurs
   */
  private boolean writeRecord(Record r)
    throws IOException
  {
    boolean cont = true;
    int pos = reader.getPos();
    int code = r.getCode();

    if (bofs == 0)
    {
      cont = (r.getType() == Type.BOF);
    }

    if (!cont)
    {
      return cont;
    }

    if (r.getType() == Type.BOF)
    {
      bofs++;
    }
    
    if (r.getType() == Type.EOF)
    {
      bofs--;
    }

    StringBuffer buf = new StringBuffer();

    // Write out the record header
    writeSixDigitvalue(pos, buf);
    buf.append(" [");
    buf.append(recordNames.get(r.getType()));
    buf.append("]");
    buf.append("  (0x");
    buf.append(Integer.toHexString(code));
    buf.append(")");

    if (code == Type.XF.value)
    {
      buf.append(" (0x");
      buf.append(Integer.toHexString(xfIndex));
      buf.append(")");
      xfIndex++;
    }

    if (code == Type.FONT.value)
    {
      if (fontIndex == 4)
      {
        fontIndex++;
      }

      buf.append(" (0x");
      buf.append(Integer.toHexString(fontIndex));
      buf.append(")");
      fontIndex++;
    }

    writer.write(buf.toString());
    writer.newLine();

    byte[] standardData = new byte[4];
    standardData[0] = (byte) (code & 0xff);
    standardData[1] = (byte) ((code & 0xff00) >> 8);
    standardData[2] = (byte) (r.getLength() & 0xff);
    standardData[3] = (byte) ((r.getLength() & 0xff00) >> 8);
    byte[] recordData = r.getData();
    byte[] data = new byte[standardData.length + recordData.length];
    System.arraycopy(standardData, 0, data, 0, standardData.length);
    System.arraycopy(recordData, 0, data, 
                     standardData.length, recordData.length);

    int byteCount = 0;
    int lineBytes = 0;

    while (byteCount < data.length)
    {
      buf = new StringBuffer();
      writeSixDigitvalue(pos+byteCount, buf);
      buf.append("   ");

      lineBytes = Math.min(bytesPerLine, data.length - byteCount);

      for (int i = 0; i < lineBytes ; i++)
      {
        writeByte(data[i+byteCount], buf);
        buf.append(' ');
      }

      // Perform any padding
      if (lineBytes < bytesPerLine)
      {
        for(int i = 0; i < bytesPerLine - lineBytes; i++)
        {
          buf.append("   ");
        }
      }

      buf.append("  ");

      for (int i = 0 ; i < lineBytes; i++)
      {
        char c = (char) data[i+byteCount];
        if (c < ' ' || c > 'z')
        {
          c = '.';
        }
        buf.append(c);
      }

      byteCount+= lineBytes;

      writer.write(buf.toString());
      writer.newLine();
    }

    return cont;
  }

  /**
   * Writes the string passed in as a minimum of four digits
   */
  private void writeSixDigitvalue(int pos, StringBuffer buf)
  {
    String val = Integer.toHexString(pos);

    for (int i = 6; i > val.length() ; i--)
    {
      buf.append('0');
    }
    buf.append(val);
  }

  /**
   * Writes the string passed in as a minimum of four digits
   */
  private void writeByte(byte val, StringBuffer buf)
  {    
    String sv = Integer.toHexString((val & 0xff));

    if (sv.length() == 1)
    {
      buf.append('0');
    }
    buf.append(sv);
  }
}
