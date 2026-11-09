// Presentation-only additions. Original API and CRUD code remains unchanged.
document.getElementById('hotelYear').textContent = new Date().getFullYear();
document.querySelectorAll('[data-manage]').forEach(button => {
  button.addEventListener('click', () => {
    document.body.classList.remove('guest-view');
    navigate(button.dataset.manage);
    window.scrollTo({top: 0, behavior: 'instant'});
    document.getElementById('pageTitle').focus({preventScroll: true});
  });
});
document.getElementById('pageTitle').setAttribute('tabindex', '-1');
document.getElementById('returnToHotel').addEventListener('click', () => {
  document.body.classList.add('guest-view');
  window.scrollTo({top: 0, behavior: 'instant'});
});
