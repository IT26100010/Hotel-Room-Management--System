const API_BASE = "http://localhost:8080";

const modules = {
  customers: {
    title: "Customers", singular: "Customer", endpoint: "/api/customers", idKey: "userId",
    fields: [
      ["userId","Customer ID","text"],["name","Name","text"],["email","Email","email"],
      ["phone","Phone","text"],["password","Password","password"],["address","Address","text"]
    ]
  },
  rooms: {
    title: "Rooms", singular: "Room", endpoint: "/api/rooms", idKey: "roomId",
    fields: [
      ["roomId","Room ID","text"],["roomNumber","Room Number","text"],
      ["basePrice","Base Price","number"],["capacity","Capacity","number"],
      ["availability","Availability","select",["AVAILABLE","BOOKED","MAINTENANCE"]],
      ["roomType","Room Type","select",["STANDARD","DELUXE","SUITE"]]
    ]
  },
  reservations: {
    title: "Reservations", singular: "Reservation", endpoint: "/api/reservations", idKey: "reservationId",
    fields: [
      ["reservationId","Reservation ID","text"],["customerId","Customer ID","text"],
      ["roomId","Room ID","text"],["checkInDate","Check-in","date"],
      ["checkOutDate","Check-out","date"],["numberOfGuests","Guests","number"],
      ["status","Status","select",["PENDING","CONFIRMED","CANCELLED"]],
      ["totalAmount","Total Amount","number"]
    ]
  },
  payments: {
    title: "Payments", singular: "Payment", endpoint: "/api/payments", idKey: "paymentId",
    fields: [
      ["paymentId","Payment ID","text"],["reservationId","Reservation ID","text"],
      ["amount","Amount","number"],["paymentDate","Payment Date","date"],
      ["paymentStatus","Status","select",["PENDING","PAID","FAILED"]],
      ["paymentType","Payment Type","select",["CASH","CARD","ONLINE"]]
    ]
  },
  services: {
    title: "Hotel Services", singular: "Service", endpoint: "/api/services", idKey: "serviceId",
    fields: [
      ["serviceId","Service ID","text"],["serviceName","Service Name","text"],
      ["description","Description","text"],["price","Price","number"],
      ["availability","Availability","select",["AVAILABLE","UNAVAILABLE"]],
      ["serviceType","Service Type","select",["FOOD","TRANSPORT","LAUNDRY"]]
    ]
  },
  reviews: {
    title: "Reviews", singular: "Review", endpoint: "/api/reviews", idKey: "reviewId",
    fields: [
      ["reviewId","Review ID","text"],["customerId","Customer ID","text"],
      ["reservationId","Reservation ID","text"],["rating","Rating (1-5)","number"],
      ["comment","Comment","text"],["reviewDate","Review Date","date"],
      ["reviewType","Review Type","select",["PUBLIC","VERIFIED"]]
    ]
  }
};

let state = { page: "dashboard", records: {}, editing: null };

document.querySelectorAll(".nav-item").forEach(btn => {
  btn.addEventListener("click", () => navigate(btn.dataset.page));
});
document.getElementById("refreshBtn").onclick = () => renderPage();
document.getElementById("mobileMenu").onclick = () => document.querySelector(".sidebar").classList.toggle("open");

function navigate(page) {
  state.page = page;
  document.querySelectorAll(".nav-item").forEach(x => x.classList.toggle("active", x.dataset.page === page));
  document.querySelector(".sidebar").classList.remove("open");
  document.getElementById("pageTitle").textContent = page === "dashboard" ? "Dashboard" : modules[page].title;
  renderPage();
}

async function api(path, options = {}) {
  const res = await fetch(API_BASE + path, {
    ...options,
    headers: {"Content-Type":"application/json", ...(options.headers || {})}
  });
  if (!res.ok) {
    const txt = await res.text();
    throw new Error(txt || `${res.status} ${res.statusText}`);
  }
  if (res.status === 204) return null;
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

async function checkApi() {
  const dot = document.getElementById("apiDot"), status = document.getElementById("apiStatus");
  try {
    const response = await fetch(API_BASE + "/api/customers", {method:"GET"});
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    dot.style.background = "#22c55e"; status.textContent = "Backend connected";
  } catch {
    dot.style.background = "#ef4444"; status.textContent = "Backend offline";
  }
}

function renderPage() {
  if (state.page === "dashboard") renderDashboard();
  else renderModule(state.page);
}

function renderDashboard() {
  document.getElementById("app").innerHTML = `
    <div class="content">
      <section class="hero">
        <div>
          <h2>Welcome to GrandStay</h2>
          <p>Hotel Room Reservation System dashboard. Manage customers, rooms, reservations, payments, services and guest reviews from one place.</p>
        </div>
        <div class="hero-badge"><strong>6</strong><span>MANAGEMENT MODULES</span></div>
      </section>
      <div class="stats" id="stats"></div>
      <section class="panel">
        <div class="panel-head"><h3>Quick Actions</h3></div>
        <div class="quick">
          ${Object.entries(modules).map(([key,m]) => `<button class="outline-btn" onclick="navigate('${key}')">＋ ${m.title}</button>`).join("")}
        </div>
      </section>
      <section class="grid-cards">
        ${Object.entries(modules).map(([key,m]) => `
          <article class="module-card">
            <h3>${m.title}</h3>
            <p>CRUD management for the ${m.title.toLowerCase()} module.</p>
            <button class="small-btn" onclick="navigate('${key}')">Open Module →</button>
          </article>`).join("")}
      </section>
    </div>`;
  loadStats();
}

async function loadStats() {
  const stats = document.getElementById("stats");
  const results = await Promise.all(Object.entries(modules).map(async ([key,m]) => {
    try {
      const data = await api(m.endpoint);
      return [key, Array.isArray(data) ? data.length : 0];
    } catch { return [key, 0]; }
  }));
  stats.innerHTML = results.map(([key,count]) => `
    <div class="stat"><span class="label">${modules[key].title}</span><strong>${count}</strong><small>Records</small></div>
  `).join("");
}

async function renderModule(key) {
  const m = modules[key];
  document.getElementById("app").innerHTML = `
    <div class="content">
      <section class="panel">
        <div class="panel-head">
          <div><h3>${m.title} Management</h3><div style="font-size:12px;color:#6b7280;margin-top:4px">Create, read, update and delete records</div></div>
          <button class="primary-btn" onclick="openForm('${key}')">＋ Add ${m.singular}</button>
        </div>
        <div class="toolbar">
          <input id="search" class="search" placeholder="Search ${m.title.toLowerCase()}..." oninput="filterTable('${key}')">
          <button class="outline-btn" onclick="loadModule('${key}')">↻ Reload</button>
        </div>
        <div id="tableArea" class="table-wrap"><div class="loading">Loading...</div></div>
      </section>
    </div>`;
  await loadModule(key);
}

async function loadModule(key) {
  const area = document.getElementById("tableArea");
  try {
    const data = await api(modules[key].endpoint);
    state.records[key] = Array.isArray(data) ? data : [];
    renderTable(key, state.records[key]);
  } catch (e) {
    area.innerHTML = `<div class="error">Could not load data. Make sure Spring Boot is running on <b>${API_BASE}</b>.<br><br>${escapeHtml(e.message)}</div>`;
  }
}

function filterTable(key) {
  const q = (document.getElementById("search")?.value || "").toLowerCase();
  const rows = (state.records[key] || []).filter(x => JSON.stringify(x).toLowerCase().includes(q));
  renderTable(key, rows);
}

function renderTable(key, rows) {
  const m = modules[key], area = document.getElementById("tableArea");
  if (!area) return;
  if (!rows.length) { area.innerHTML = `<div class="empty">No records found. Click “Add ${m.singular}” to create one.</div>`; return; }
  const keys = m.fields.map(f => f[0]);
  area.innerHTML = `<table class="data-table">
    <thead><tr>${keys.map(k => `<th>${label(k)}</th>`).join("")}<th>Actions</th></tr></thead>
    <tbody>${rows.map(row => `<tr>
      ${keys.map(k => `<td>${formatValue(row[k],k)}</td>`).join("")}
      <td>
        <button class="small-btn" onclick="openForm('${key}', '${escapeAttr(row[m.idKey])}')">Edit</button>
        <button class="small-btn danger-btn" onclick="deleteRecord('${key}', '${escapeAttr(row[m.idKey])}')">Delete</button>
      </td>
    </tr>`).join("")}</tbody>
  </table>`;
}

function openForm(key, id = null) {
  const m = modules[key], existing = id ? (state.records[key] || []).find(x => String(x[m.idKey]) === String(id)) : null;
  state.editing = {key, id};
  const formFields = m.fields.map(f => {
    const [name, title, type, options] = f;
    const value = name === "paymentType" ? (existing?.paymentMethod ?? "")
      : name === "serviceType" ? (existing?.serviceType ?? "")
      : existing?.[name] ?? "";
    if (type === "select") return `<div class="field"><label>${title}</label><select name="${name}">${options.map(o => `<option ${String(value).toUpperCase()===o ? "selected":""}>${o}</option>`).join("")}</select></div>`;
    return `<div class="field"><label>${title}</label><input name="${name}" type="${type}" value="${escapeAttr(value)}" ${id && name===m.idKey ? "readonly":""}></div>`;
  }).join("");
  const modal = document.createElement("div");
  modal.className = "modal show"; modal.id = "formModal";
  modal.innerHTML = `<div class="modal-box">
    <div class="modal-head"><h2>${id ? "Edit" : "Add"} ${m.singular}</h2><button class="close" onclick="closeModal()">×</button></div>
    <form id="recordForm"><div class="form-grid">${formFields}</div>
      <div class="form-actions"><button type="submit" class="primary-btn">${id ? "Update" : "Create"} ${m.singular}</button><button type="button" class="outline-btn" onclick="closeModal()">Cancel</button></div>
    </form>
  </div>`;
  document.body.appendChild(modal);
  document.getElementById("recordForm").onsubmit = e => { e.preventDefault(); saveRecord(key, id, new FormData(e.target)); };
}

async function saveRecord(key, id, fd) {
  const m = modules[key], obj = {};
  m.fields.forEach(f => {
    let v = fd.get(f[0]);
    if (f[2] === "number") v = v === "" ? null : Number(v);
    obj[f[0]] = v;
  });
  // Remove UI-only type selector from payload; controller routes determine subtype.
  delete obj.roomType; delete obj.paymentType; delete obj.serviceType; delete obj.reviewType;

  try {
    let path = m.endpoint, method = id ? "PUT" : "POST";
    if (key === "rooms" && !id) path += "/" + String(fd.get("roomType") || "STANDARD").toLowerCase();
    if (key === "rooms" && id) path += "/" + encodeURIComponent(id) + "/" + String(fd.get("roomType") || "STANDARD").toLowerCase();
    if (key === "payments" && !id) path += "/" + String(fd.get("paymentType") || "CASH").toLowerCase();
    if (key === "payments" && id) path += "/" + String(fd.get("paymentType") || "CASH").toLowerCase() + "/" + encodeURIComponent(id);
    if (key === "services" && !id) path += "/" + String(fd.get("serviceType") || "FOOD").toLowerCase();
    if (key === "services" && id) path += "/" + encodeURIComponent(id) + "/" + String(fd.get("serviceType") || "FOOD").toLowerCase();
    if (key === "reviews" && !id) path += "/" + String(fd.get("reviewType") || "PUBLIC").toLowerCase();
    if (key === "reviews" && id) path += "/" + String(fd.get("reviewType") || "PUBLIC").toLowerCase() + "/" + encodeURIComponent(id);
    if (id && !["payments","services","reviews","rooms"].includes(key)) path += "/" + encodeURIComponent(id);

    await api(path, {method, body: JSON.stringify(obj)});
    closeModal(); toast(id ? "Updated successfully" : "Created successfully"); await loadModule(key);
  } catch(e) { toast("Error: " + e.message); }
}

async function deleteRecord(key, id) {
  if (!confirm(`Delete ${modules[key].singular} ${id}?`)) return;
  try {
    await api(modules[key].endpoint + "/" + encodeURIComponent(id), {method:"DELETE"});
    toast("Deleted successfully"); await loadModule(key);
  } catch(e) { toast("Error: " + e.message); }
}

function closeModal(){ document.getElementById("formModal")?.remove(); }
function label(k){return k.replace(/([A-Z])/g," $1").replace(/^./,x=>x.toUpperCase())}
function formatValue(v,k){if(v===null||v===undefined||v==="")return "—"; if(k.toLowerCase().includes("status")||k==="availability")return `<span class="badge ${String(v).toLowerCase().includes("available")||String(v).toLowerCase().includes("paid")||String(v).toLowerCase().includes("confirmed")?"green":String(v).toLowerCase().includes("cancel")||String(v).toLowerCase().includes("failed")?"red":"gold"}">${escapeHtml(v)}</span>`; return escapeHtml(v)}
function escapeHtml(v){return String(v??"").replace(/[&<>"']/g,c=>({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#039;"}[c]))}
function escapeAttr(v){return escapeHtml(v)}
function toast(msg){const t=document.getElementById("toast");t.textContent=msg;t.classList.add("show");setTimeout(()=>t.classList.remove("show"),2800)}

renderPage();
checkApi();
setInterval(checkApi, 10000);
