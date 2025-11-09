// Global variables
let autoRefreshInterval;
let sourceChart, typeChart;

// Initialize charts
function initializeCharts() {
    const sourceCtx = document.getElementById('sourceChart').getContext('2d');
    sourceChart = new Chart(sourceCtx, {
        type: 'doughnut',
        data: {
            labels: ['Web Server', 'Mobile App', 'Sensor', 'API Server'],
            datasets: [{
                data: [0, 0, 0, 0],
                backgroundColor: ['#ff6b6b', '#4ecdc4', '#45b7d1', '#96ceb4']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });

    const typeCtx = document.getElementById('typeChart').getContext('2d');
    typeChart = new Chart(typeCtx, {
        type: 'bar',
        data: {
            labels: ['PAGE_VIEW', 'USER_LOGIN', 'PURCHASE', 'SEARCH', 'ERROR', 'SENSOR_READING'],
            datasets: [{
                label: 'Events',
                data: [0, 0, 0, 0, 0, 0],
                backgroundColor: '#4299e1'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// Update charts with data
function updateCharts(eventsBySource, eventsByType) {
    if (sourceChart) {
        sourceChart.data.datasets[0].data = [
            eventsBySource.website || 0,
            eventsBySource['mobile-app'] || 0,
            eventsBySource['sensor-1'] || 0,
            eventsBySource['api-server'] || 0
        ];
        sourceChart.update();
    }

    if (typeChart) {
        typeChart.data.datasets[0].data = [
            eventsByType.PAGE_VIEW || 0,
            eventsByType.USER_LOGIN || 0,
            eventsByType.PURCHASE || 0,
            eventsByType.SEARCH || 0,
            eventsByType.ERROR || 0,
            eventsByType.SENSOR_READING || 0
        ];
        typeChart.update();
    }
}

// Load recent events
async function loadRecentEvents() {
    try {
        showLoading('eventsList');
        const response = await fetch('/api/v1/events/all');
        const events = await response.json();
        
        displayEvents(events);
        updateEventStats(events);
    } catch (error) {
        showAlert('Error loading events: ' + error.message, 'error');
    } finally {
        hideLoading('eventsList');
    }
}

// Display events in the list
function displayEvents(events) {
    const eventsList = document.getElementById('eventsList');
    const sourceFilter = document.getElementById('sourceFilter').value;
    const typeFilter = document.getElementById('typeFilter').value;
    
    let filteredEvents = events;
    
    // Apply filters
    if (sourceFilter !== 'all') {
        filteredEvents = filteredEvents.filter(event => event.source === sourceFilter);
    }
    
    if (typeFilter !== 'all') {
        filteredEvents = filteredEvents.filter(event => event.eventType === typeFilter);
    }
    
    // Show only last 10 events
    const recentEvents = filteredEvents.slice(-10).reverse();
    
    if (recentEvents.length === 0) {
        eventsList.innerHTML = '<div class="event-item">No events match your filters</div>';
        return;
    }
    
    eventsList.innerHTML = recentEvents.map(event => `
        <div class="event-item">
            <span class="event-source">${event.source}</span>
            <span class="event-type">${event.eventType}</span>
            <span class="event-time">${formatTime(event.timestamp)}</span>
            <div class="event-payload">${JSON.stringify(event.payload)}</div>
        </div>
    `).join('');
}

// Update event statistics
function updateEventStats(events) {
    const eventsBySource = {
        'website': 0,
        'mobile-app': 0,
        'sensor-1': 0,
        'api-server': 0
    };
    
    const eventsByType = {
        'PAGE_VIEW': 0,
        'USER_LOGIN': 0,
        'PURCHASE': 0,
        'SEARCH': 0,
        'ERROR': 0,
        'SENSOR_READING': 0
    };
    
    events.forEach(event => {
        eventsBySource[event.source] = (eventsBySource[event.source] || 0) + 1;
        eventsByType[event.eventType] = (eventsByType[event.eventType] || 0) + 1;
    });
    
    updateCharts(eventsBySource, eventsByType);
}

// Event form submission
document.getElementById('eventForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const formData = new FormData(this);
    const eventData = {
        source: formData.get('source'),
        eventType: formData.get('eventType'),
        payload: JSON.parse(formData.get('payload'))
    };
    
    try {
        showLoading('eventForm');
        const response = await fetch('/api/v1/events/ingest', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(eventData)
        });
        
        const result = await response.json();
        
        if (response.ok) {
            showAlert('Event sent successfully!', 'success');
            // Clear form
            this.reset();
            // Refresh events
            setTimeout(loadRecentEvents, 500);
        } else {
            throw new Error(result.message || 'Failed to send event');
        }
    } catch (error) {
        showAlert('Error sending event: ' + error.message, 'error');
    } finally {
        hideLoading('eventForm');
    }
});

// Filter events
function filterEvents() {
    loadRecentEvents();
}

function clearFilters() {
    document.getElementById('sourceFilter').value = 'all';
    document.getElementById('typeFilter').value = 'all';
    loadRecentEvents();
}

// System status
function updateSystemStatus() {
    const statusGrid = document.getElementById('statusGrid');
    statusGrid.innerHTML = `
        <div class="status-item">Spring Boot</div>
        <div class="status-item">Kafka</div>
        <div class="status-item">MongoDB</div>
        <div class="status-item">Spark</div>
    `;
}

// Health check
async function checkSystemHealth() {
    try {
        const response = await fetch('/api/v1/events/health');
        const health = await response.json();
        
        document.getElementById('healthStatus').innerHTML = `
            <div class="alert success">
                ✅ System Status: ${health.status}<br>
                Service: ${health.service}<br>
                Time: ${health.timestamp}
            </div>
        `;
    } catch (error) {
        document.getElementById('healthStatus').innerHTML = `
            <div class="alert error">
                ❌ Health check failed: ${error.message}
            </div>
        `;
    }
}

// Sample data
async function loadSampleEvents() {
    const sampleEvents = [
        {
            source: 'website',
            eventType: 'PAGE_VIEW',
            payload: { userId: 'user123', page: '/home', duration: 45 }
        },
        {
            source: 'mobile-app',
            eventType: 'USER_LOGIN',
            payload: { userId: 'user456', device: 'iPhone', appVersion: '2.1.0' }
        },
        {
            source: 'website',
            eventType: 'PURCHASE',
            payload: { userId: 'user123', orderId: 'ORD001', amount: 99.99 }
        },
        {
            source: 'sensor-1',
            eventType: 'SENSOR_READING',
            payload: { temperature: 22.5, humidity: 45, battery: 85 }
        }
    ];
    
    for (const event of sampleEvents) {
        try {
            await fetch('/api/v1/events/ingest', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(event)
            });
        } catch (error) {
            console.error('Failed to send sample event:', error);
        }
    }
    
    showAlert('Sample events loaded successfully!', 'success');
    setTimeout(loadRecentEvents, 1000);
}

// Enhanced confirmation dialog
function showConfirmationDialog(message) {
    return new Promise((resolve) => {
        const dialog = document.createElement('div');
        dialog.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        `;
        
        dialog.innerHTML = `
            <div style="
                background: white;
                padding: 30px;
                border-radius: 10px;
                text-align: center;
                max-width: 400px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.3);
            ">
                <h3 style="color: #e53e3e; margin-bottom: 15px;">⚠️ Warning</h3>
                <p style="margin-bottom: 20px; line-height: 1.5;">${message}</p>
                <div style="display: flex; gap: 10px; justify-content: center;">
                    <button onclick="this.closest('div[style]').parentElement.remove(); resolve(false)" 
                            style="padding: 10px 20px; border: 1px solid #ccc; background: white; border-radius: 5px; cursor: pointer;">
                        Cancel
                    </button>
                    <button onclick="this.closest('div[style]').parentElement.remove(); resolve(true)" 
                            style="padding: 10px 20px; background: #e53e3e; color: white; border: none; border-radius: 5px; cursor: pointer;">
                        Delete All
                    </button>
                </div>
            </div>
        `;
        
        document.body.appendChild(dialog);
    });
}

// Clear all events - WORKING VERSION
// Clear all events - FIXED VERSION
async function clearAllEvents() {
    const confirmed = await showConfirmationDialog(
        'This will permanently delete ALL events from the database. This action cannot be undone. Are you sure you want to continue?'
    );
    
    if (!confirmed) {
        return;
    }
    
    try {
        showLoading('eventsList');
        
        // Use GET request instead of POST
        const response = await fetch('/reset-events');
        
        if (response.ok) {
            showAlert('🎉 All events cleared successfully! Dashboard reset to zero.', 'success');
            // Refresh the dashboard after a short delay
            setTimeout(() => {
                refreshDashboard();
            }, 1000);
        } else {
            throw new Error('Server returned error: ' + response.status);
        }
    } catch (error) {
        showAlert('❌ Error clearing events: ' + error.message, 'error');
        console.error('Reset error:', error);
    } finally {
        hideLoading('eventsList');
    }
}

// Export events
async function exportEvents() {
    try {
        const response = await fetch('/api/v1/events/all');
        const events = await response.json();
        
        const dataStr = JSON.stringify(events, null, 2);
        const dataBlob = new Blob([dataStr], { type: 'application/json' });
        
        const link = document.createElement('a');
        link.href = URL.createObjectURL(dataBlob);
        link.download = 'events-export.json';
        link.click();
        
        showAlert('Events exported successfully!', 'success');
    } catch (error) {
        showAlert('Error exporting events: ' + error.message, 'error');
    }
}

// Copy to clipboard
function copyToClipboard(text) {
    navigator.clipboard.writeText(text).then(() => {
        showAlert('Copied to clipboard: ' + text, 'success');
    }).catch(err => {
        showAlert('Failed to copy: ' + err, 'error');
    });
}

// Refresh dashboard
function refreshDashboard() {
    loadRecentEvents();
    updateSystemStatus();
    document.getElementById('lastUpdated').textContent = new Date().toLocaleTimeString();
}

// Auto-refresh
function startAutoRefresh() {
    if (document.getElementById('autoRefresh').checked) {
        autoRefreshInterval = setInterval(refreshDashboard, 10000);
    }
}

function toggleAutoRefresh() {
    if (document.getElementById('autoRefresh').checked) {
        autoRefreshInterval = setInterval(refreshDashboard, 10000);
        showAlert('Auto-refresh enabled', 'info');
    } else {
        clearInterval(autoRefreshInterval);
        showAlert('Auto-refresh disabled', 'info');
    }
}

// Utility functions
function showAlert(message, type) {
    const alertContainer = document.getElementById('alertContainer');
    const alert = document.createElement('div');
    alert.className = `alert ${type}`;
    alert.textContent = message;
    
    alertContainer.appendChild(alert);
    
    setTimeout(() => {
        alert.remove();
    }, 5000);
}

function showLoading(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
        element.classList.add('loading');
    }
}

function hideLoading(elementId) {
    const element = document.getElementById(elementId);
    if (element) {
        element.classList.remove('loading');
    }
}

function formatTime(timestamp) {
    try {
        const date = new Date(timestamp);
        return date.toLocaleTimeString();
    } catch (e) {
        return 'Invalid time';
    }
}

// Initialize when page loads
document.addEventListener('DOMContentLoaded', function() {
    initializeCharts();
    loadRecentEvents();
    updateSystemStatus();
    
    // Set up auto-refresh if checked
    if (document.getElementById('autoRefresh') && document.getElementById('autoRefresh').checked) {
        startAutoRefresh();
    }
    
    // Add event listeners for filters
    const sourceFilter = document.getElementById('sourceFilter');
    const typeFilter = document.getElementById('typeFilter');
    
    if (sourceFilter) {
        sourceFilter.addEventListener('change', filterEvents);
    }
    
    if (typeFilter) {
        typeFilter.addEventListener('change', filterEvents);
    }
});

// Handle page visibility changes for auto-refresh
document.addEventListener('visibilitychange', function() {
    if (document.hidden) {
        clearInterval(autoRefreshInterval);
    } else if (document.getElementById('autoRefresh') && document.getElementById('autoRefresh').checked) {
        startAutoRefresh();
    }
});