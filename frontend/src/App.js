import React, { useState } from 'react';
import TicketForm from './components/TicketForm';
import TicketList from './components/TicketList';
import { Container, Button, Typography } from '@mui/material';

const App = () => {
    const [tickets, setTickets] = useState([]);
    const [showTickets, setShowTickets] = useState(false);

    const handleTicketSubmit = (ticketData) => {
        // Here you would typically send the ticketData to your backend API
        // For now, we will just add it to the local state
        setTickets([...tickets, ticketData]);
    };

    const toggleShowTickets = () => {
        setShowTickets(!showTickets);
    };

    return (
        <Container maxWidth="sm">
            <Typography variant="h3" align="center" gutterBottom>
                Ticket Management System
            </Typography>
            <TicketForm onSubmit={handleTicketSubmit} />
            <Button 
                variant="outlined" 
                color="primary" 
                onClick={toggleShowTickets} 
                fullWidth
            >
                {showTickets ? 'Hide Previous Tickets' : 'Show Previous Tickets'}
            </Button>
            {showTickets && <TicketList tickets={tickets} />}
        </Container>
    );
};

export default App;