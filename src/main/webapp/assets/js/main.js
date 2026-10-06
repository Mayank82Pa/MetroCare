/**
 * Online Healthcare Management System - Client Side Scripts
 */

document.addEventListener('DOMContentLoaded', () => {
  // 1. Auto dismiss alerts after 5 seconds
  const alerts = document.querySelectorAll('.alert');
  alerts.forEach(alert => {
    setTimeout(() => {
      alert.style.opacity = '0';
      alert.style.transition = 'opacity 0.5s ease';
      setTimeout(() => alert.remove(), 500);
    }, 5000);
  });

  // 2. Interactive Time Slot Selector
  const slotButtons = document.querySelectorAll('.slot-btn');
  const appointmentTimeInput = document.getElementById('appointmentTimeInput');

  slotButtons.forEach(btn => {
    btn.addEventListener('click', (e) => {
      e.preventDefault();
      slotButtons.forEach(b => b.classList.remove('selected'));
      btn.classList.add('selected');
      const timeVal = btn.getAttribute('data-time');
      if (appointmentTimeInput) {
        appointmentTimeInput.value = timeVal;
      }
    });
  });

  // 3. Quick Confirmation for Destructive Actions
  const confirmActions = document.querySelectorAll('[data-confirm]');
  confirmActions.forEach(el => {
    el.addEventListener('click', (e) => {
      const msg = el.getAttribute('data-confirm') || 'Are you sure you want to proceed?';
      if (!confirm(msg)) {
        e.preventDefault();
      }
    });
  });
});
