# Backend

Run with JDK 17 from IntelliJ IDEA. Set the run configuration working directory to this `backend` folder, then run `src/main/java/com/hotel/HotelRoomReservationApplication.java`. API base: http://localhost:8080.

The repositories use relative `data/*.txt` paths. Keeping the working directory set to this folder ensures records are saved under `backend/data/`.

For the frontend, open the sibling `frontend/index.html` with VS Code Live Server. The frontend calls `http://localhost:8080`, and `WebConfig` permits common Live Server origins on ports 5500 and 5501.
