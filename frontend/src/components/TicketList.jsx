import React from 'react';

const TicketList = ({ tickets }) => {
    return (
        <div>
            <h2>Previous Tickets</h2>
            {tickets.length === 0 ? (
                <p>No tickets found.</p>
            ) : (
                <ul>
                    {tickets.map((ticket, index) => (
                        <li key={index}>
                            <strong>{ticket.title}</strong> - {ticket.category}
                            <p>{ticket.description}</p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default TicketList;