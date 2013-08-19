package aidl.core.API;

import aidl.sp.API.Ticket;

interface OnTicketChange {
	void addTicket(in String dummy, in Ticket item);
	void removeTicket(in Ticket item);
}