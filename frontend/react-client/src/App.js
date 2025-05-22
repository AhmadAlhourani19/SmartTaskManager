import React, { useEffect, useState } from 'react';
import TaskForm from './components/TaskForm';
import TaskList from './components/TaskList';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);

  const fetchTasks = async () => {
    try {
      const response = await fetch('http://localhost:8080/tasks');
      const data = await response.json();
      setTasks(data);
    } catch (err) {
      alert("Failed to fetch tasks");
    }
  };
  
  const handleTaskDeleted = (id) => {
    setTasks(prev => prev.filter(task => task.id !== id));
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  const handleTaskCreated = (newTask) => {
    setTasks(prev => [...prev, newTask]);
  };

  return (
    <div className="max-w-xl mx-auto mt-10 p-6 bg-white shadow-lg rounded-lg">
      <h1 className="text-3xl font-bold text-center mb-6 text-gray-800">Smart Task Manager</h1>
      <TaskForm onTaskCreated={handleTaskCreated} />
      <TaskList tasks={tasks} onTaskDeleted={handleTaskDeleted} />
    </div>
  );
}

export default App;
