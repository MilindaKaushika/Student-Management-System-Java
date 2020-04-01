/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.LinkedList;
/*  5:   */ 
/*  6:   */ public class Phonebook
/*  7:   */ {
/*  8:   */   private Collection<Contact> contacts;
/*  9:   */   
/* 10:   */   public Phonebook()
/* 11:   */   {
/* 12:38 */     this.contacts = new LinkedList();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Collection<Contact> getContacts()
/* 16:   */   {
/* 17:47 */     return this.contacts;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Collection<Contact> getContacts(Contact.ContactLocation type)
/* 21:   */   {
/* 22:58 */     Collection<Contact> filteredContacts = new LinkedList();
/* 23:59 */     String loc = Contact.convertTypeToLocation(type);
/* 24:60 */     if (loc.length() > 0) {
/* 25:62 */       for (Contact c : getContacts()) {
/* 26:64 */         if (c.getMemLoc().equalsIgnoreCase(loc)) {
/* 27:64 */           filteredContacts.add(c);
/* 28:   */         }
/* 29:   */       }
/* 30:   */     }
/* 31:67 */     return filteredContacts;
/* 32:   */   }
/* 33:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.Phonebook
 * JD-Core Version:    0.7.0.1
 */