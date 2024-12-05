import React, { useState, useEffect } from 'react';
import {
    TextField,
    Button,
    MenuItem,
    Typography,
    Paper,
    Box,
    Snackbar
} from '@mui/material';

const TicketForm = ({ onSubmit }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [category, setCategory] = useState('General');
    const [email, setEmail] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [alertMessage, setAlertMessage] = useState('');
    const [alertVisible, setAlertVisible] = useState(false);
    const [touchedEmail, setTouchedEmail] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const ticketData = { title, description, category, email };

        // Set loading state
        setLoading(true);
        setError(null);

        try {
            // Make POST request to the API
            const response = await fetch('http://localhost:8080/api/tickets/submit', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(ticketData),
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            // Get the response message
            const data = await response.json(); // Expecting a JSON object here
            setAlertMessage(data.message); // Assuming your backend returns a message field
            setAlertVisible(true); // Show alert notification

            // Optionally call onSubmit to update local state or perform other actions
            onSubmit(ticketData);

            // Clear form fields after submission
            setTitle('');
            setDescription('');
            setCategory('General');
            setEmail('');
            setTouchedEmail(false); // Reset touched state after submission
        } catch (error) {
            setError(error.message || 'Something went wrong');
        } finally {
            // Reset loading state
            setLoading(false);
        }
    };

    // Effect to hide the alert after 5 seconds
    useEffect(() => {
        if (alertVisible) {
            const timer = setTimeout(() => {
                setAlertVisible(false);
            }, 5000);
            return () => clearTimeout(timer); // Cleanup timer on unmount or when alert is hidden
        }
    }, [alertVisible]);

    // Email validation logic
    const isValidEmail = (email) => /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email);

    return (
        <Paper elevation={3} sx={{ padding: '20px', marginBottom: '20px' }}>
            <Typography variant="h5" gutterBottom>
                Create New Ticket
            </Typography>
            {error && <Typography color="error">{error}</Typography>}
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
                        label="Email"
                        variant="outlined"
                        type="email"
                        fullWidth
                        value={email}
                        onChange={(e) => {
                            setEmail(e.target.value);
                            if (!touchedEmail) {
                                setTouchedEmail(true); // Mark as touched when user starts typing
                            }
                        }}
                        required
                        error={touchedEmail && !isValidEmail(email)} // Show error only if touched and invalid
                        helperText={touchedEmail && !isValidEmail(email) ? "Enter a valid email address" : ""}
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
                        disabled={loading} // Disable button while loading
                    >
                        {loading ? 'Submitting...' : 'Submit Ticket'}
                    </Button>
                </Box>
            </form>

            {/* Snackbar for alert notification */}
            <Snackbar 
                open={alertVisible} 
                autoHideDuration={5000} 
                onClose={() => setAlertVisible(false)}
                message={alertMessage}
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
            />
        </Paper>
    );
};

export default TicketForm;