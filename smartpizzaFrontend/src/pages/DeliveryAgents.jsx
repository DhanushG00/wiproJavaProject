import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import {
  getAllDeliveryAgents,
  getDeliveryAgentById,
  deleteDeliveryAgent,
  assignOrderToAgent,
  updateDeliveryAgentStatus,
} from "../services/deliveryAgent";

const DeliveryAgents = () => {
  const [agents, setAgents] = useState([]);
  const [searchId, setSearchId] = useState("");
  const [selectedAgent, setSelectedAgent] = useState(null);
  const [orderId, setOrderId] = useState("");
  const [status, setStatus] = useState("");

  // Load all agents
  const loadAgents = async () => {
    try {
      const res = await getAllDeliveryAgents();
      setAgents(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    loadAgents();
  }, []);

  //  Get agent by ID
  const handleSearch = async () => {
    if (!searchId) return;

    try {
      const res = await getDeliveryAgentById(searchId);
      setSelectedAgent(res.data);
    } catch (err) {
      alert("Agent not found");
      setSelectedAgent(null);
    }
  };

  //  Delete agent
  const handleDelete = async (id) => {
    if (!window.confirm("Delete this agent?")) return;

    await deleteDeliveryAgent(id);
    loadAgents();
  };

  //  Assign order
  const handleAssign = async (agentId) => {
    if (!orderId) {
      alert("Enter Order ID");
      return;
    }

    await assignOrderToAgent(agentId, orderId);
    alert("Order assigned");
  };

  //  Update status
  const handleStatusUpdate = async (agentId) => {
    if (!status) {
      alert("Select status");
      return;
    }

    await updateDeliveryAgentStatus(agentId, status);
    alert(" Status updated");
    loadAgents();
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <h3>Admin - Delivery Agents</h3>

        {/*  Search */}
        <div className="d-flex mb-3">
          <input
            className="form-control me-2"
            placeholder="Enter Agent ID"
            value={searchId}
            onChange={(e) => setSearchId(e.target.value)}
          />
          <button className="btn btn-primary" onClick={handleSearch}>
            Search
          </button>
        </div>

        {/*  Selected Agent */}
        {selectedAgent && (
          <div className="card p-3 mb-3 shadow">
            <h5>Agent Details</h5>
            <p>ID: {selectedAgent.id}</p>
            <p>Name: {selectedAgent.name}</p>
            <p>Status: {selectedAgent.status}</p>
          </div>
        )}

        {/*  Assign + Status Controls */}
        <div className="card p-3 mb-4 shadow">
          <h5>Actions</h5>

          <input
            className="form-control mb-2"
            placeholder="Enter Order ID to Assign"
            value={orderId}
            onChange={(e) => setOrderId(e.target.value)}
          />

          <select
            className="form-control mb-2"
            onChange={(e) => setStatus(e.target.value)}
          >
            <option value="">Select Status</option>
            <option value="AVAILABLE">AVAILABLE</option>
            <option value="BUSY">BUSY</option>
          </select>
        </div>

        {/*  ALL AGENTS LIST */}
        <div className="row">
          {agents.map((agent) => (
            <div className="col-md-4" key={agent.id}>
              <div className="card p-3 mb-3 shadow">
                <h5>{agent.name}</h5>
                <p>ID: {agent.id}</p>
                <p>Status: {agent.status}</p>

                <button
                  className="btn btn-success btn-sm me-2"
                  onClick={() => handleAssign(agent.id)}
                >
                  Assign Order
                </button>

                <button
                  className="btn btn-warning btn-sm me-2"
                  onClick={() => handleStatusUpdate(agent.id)}
                >
                  Update Status
                </button>

                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleDelete(agent.id)}
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
};

export default DeliveryAgents;
