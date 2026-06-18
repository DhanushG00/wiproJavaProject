import { useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";
const Tracking = () => {

  const location = useLocation();
  const order = location.state;

  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <h3>🚚 Delivery Tracking</h3>

        <div className="card p-4 shadow">

          <h5>Order Details</h5>

          {order.items.map((item, i) => (
            <div key={i}>
              {item.name} x {item.quantity}
            </div>
          ))}

          <hr />

          <p><b>Total:</b> ₹ {order.total}</p>
          <p><b>Status:</b> Preparing 🍕</p>

          <hr />

          <h5>📍 Delivery Location</h5>
          <p>Chennai, Tamil Nadu</p>

          {/* ✅ STATIC MAP */}
          <iframe
            title="map"
            width="100%"
            height="300"
            src="https://www.openstreetmap.org/export/embed.html?bbox=80.2,13.0,80.3,13.1"
          ></iframe>

        </div>

      </div>
    </>
  );
};

export default Tracking;
