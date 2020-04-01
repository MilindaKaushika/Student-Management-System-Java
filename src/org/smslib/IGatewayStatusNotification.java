package org.smslib;

public abstract interface IGatewayStatusNotification
{
  public abstract void process(AGateway paramAGateway, AGateway.GatewayStatuses paramGatewayStatuses1, AGateway.GatewayStatuses paramGatewayStatuses2);
}


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.IGatewayStatusNotification
 * JD-Core Version:    0.7.0.1
 */