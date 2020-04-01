/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ 
/*  6:   */ public class Group
/*  7:   */ {
/*  8:   */   private String groupName;
/*  9:   */   private Collection<String> groupNumbers;
/* 10:   */   
/* 11:   */   public Group(String myGroupName)
/* 12:   */   {
/* 13:37 */     this.groupName = myGroupName;
/* 14:38 */     this.groupNumbers = new ArrayList();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:48 */     return this.groupName;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Collection<String> getNumbers()
/* 23:   */   {
/* 24:58 */     return new ArrayList(this.groupNumbers);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void addNumber(String number)
/* 28:   */   {
/* 29:69 */     this.groupNumbers.add(number);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean removeNumber(String number)
/* 33:   */   {
/* 34:82 */     return this.groupNumbers.remove(number);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void clear()
/* 38:   */   {
/* 39:90 */     this.groupNumbers.clear();
/* 40:   */   }
/* 41:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.Group
 * JD-Core Version:    0.7.0.1
 */