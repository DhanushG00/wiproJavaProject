import { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import DeliveryDashboard from "./DeliveryDashboard";

const DeliveryList = () => {

  const [deliveries, setDeliveries] = useState([]);
  const [history, setHistory] = useState([]);

  const navigate = useNavigate();

  const agentId = 2; // ✅ HARD-CODED AGENT

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      // ✅ get deliveries
      const res = await api.get("/delivery/all");

      console.log("DELIVERY DATA:", res.data); // ✅ DEBUG

      setDeliveries(Array.isArray(res.data) ? res.data : []);

      // ✅ history (filter delivered)
      const hist = res.data?.filter(d => d.status === "DELIVERED") || [];
      setHistory(hist);

    } catch (err) {
      console.error("Error fetching deliveries", err);
    }
  };

  // ✅ ACCEPT FUNCTION (FIXED)
  const accept = async (orderId) => {


        try{
      alert("✅ Delivery Assigned");

      // ✅ redirect to map
      navigate("/delivery/map", { state: { orderId } });

    } catch (err) {
      console.error(err);
      alert("❌ Assignment failed");
    }
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <h3>🚚 Active Deliveries</h3>

        <div className="row">

          {Array.isArray(deliveries) && deliveries.length > 0 ? (

            deliveries.map((d) => (

              <div className="col-md-4" key={d.id}>
                <div className="card p-3 shadow">

                  <p><b>Order ID:</b> {d.orderId ?? d.id}</p>
                  <p><b>Status:</b> {d.status ?? "NEW"}</p>

                  {/* ✅ ALWAYS SHOW ACCEPT BUTTON */}
                  <button
                    className="btn btn-primary"
                    onClick={() => accept(d.orderId ?? d.id)}
                  >
                    Accept ✅
                  </button>

                  {/* ✅ Continue button */}
                  {d.status === "ACCEPTED" && (
                    <button
                      className="btn btn-success mt-2"
                      onClick={() =>
                        navigate("/delivery/map", {
                          state: { orderId: d.orderId }
                        })
                      }
                    >
                      Continue 🚀
                    </button>
                  )}

                </div>
              </div>

            ))

          ) : (
            <p className="text-muted mt-3">No deliveries found ❌</p>
          )}

        </div>

        {/* ✅ HISTORY */}
        <h3 className="mt-5">📦 Delivery History</h3>

        <div className="row">

          {history.length > 0 ? (
            history.map((d) => (
              <div className="col-md-4" key={d.id}>
                <div className="card p-3 shadow">

                  <p><b>Order ID:</b> {d.orderId}</p>
                  <p className="text-success">Delivered ✅</p>

                </div>
              </div>
            ))
          ) : (
            <p className="text-muted mt-3">No history</p>
          )}

        </div>

      </div>
    </>
  );
};

export default DeliveryList;