package org.smslib.helper;

import java.util.EventListener;

public abstract interface SerialPortEventListener
  extends EventListener
{
  public abstract void serialEvent(SerialPortEvent paramSerialPortEvent);
}


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.helper.SerialPortEventListener
 * JD-Core Version:    0.7.0.1
 */