/*
 * Copyright (c) 2010 SimpleServer authors (see CONTRIBUTORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package simpleserver.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NBT {

  private NBTCompound root;

  NBT() {
    root = new NBTCompound("");
  }

  NBT(String filename) throws FileNotFoundException {
    this(new FileInputStream(filename));
  }

  NBT(InputStream input) {
    load(input);
  }

  NBTCompound root() {
    return root;
  }

  private void load(InputStream input) {
    try {
      root = AbstractNBTag.load(new DataInputStream(input));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void save(String filename) {
    try {
      DataOutputStream out = new DataOutputStream(getOutputStream(filename));
      root.save(out);
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected OutputStream getOutputStream(String filename) throws FileNotFoundException, IOException {
    return new FileOutputStream(filename);
  }

  @Override
  public String toString() {
    return root.toString();
  }

  public static void main(String[] args) {
    try {
      if (args.length >= 2 && args[0].equals("raw")) {
        System.out.println(new NBT(args[1]));
      } else if (args.length >= 1) {

        System.out.println(new GZipNBT(args[0]));

      } else {
        System.out.println("Usage: java -jar NBT.jar [raw] <file>");
      }
    } catch (FileNotFoundException e) {
      System.out.println("Error: No such file or dictionary");
    } catch (IOException e) {
      System.out.println("Error: File is not in GZIP format");
    }
  }

}
