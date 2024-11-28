import React, { useState } from 'react';
import {
    TextField,
    Button,
    MenuItem,
    Typography,
    Paper,
    Box,
} from '@mui/material';

const TicketForm = ({ onSubmit }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [category, setCategory] = useState('General');

    const handleSubmit = (e) => {
        e.preventDefault();
        const ticketData = { title, description, category };
        onSubmit(ticketData);
        // Clear form fields after submission
        setTitle('');
        setDescription('');
        setCategory('General');
    };

    return (
        <Paper elevation={3} sx={{ padding: '20px', marginBottom: '20px' }}>
            <Typography variant="h5" gutterBottom>
                Create New Ticket
            </Typography>
            <form onSubmit={handleSubmit}>
                <Box display="flex" flexDirection="column" gap={2}>
                    <TextField
                        label="Title"
                        variant="outlined"
                        fullWidth
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                    <TextField
                        label="Description"
                        variant="outlined"
                        multiline
                        rows={4}
                        fullWidth
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                    <TextField
                        label="Category"
                        variant="outlined"
                        select
                        fullWidth
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                    >
                        <MenuItem value="General">General</MenuItem>
                        <MenuItem value="Network Issue">Network Issue</MenuItem>
                        <MenuItem value="System Outage">System Outage</MenuItem>
                    </TextField>
                    <Button 
                        type="submit" 
                        variant="contained" 
                        color="primary"
                    >
                        Submit Ticket
                    </Button>
                </Box>
            </form>
        </Paper>
    );
};

export default TicketForm;