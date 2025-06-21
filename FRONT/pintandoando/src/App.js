import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './components/Home/Home';
import CreateRoom from './components/Create/CreateRoom';
import RoomList from './components/List/ListRoom';
import RegisterUser from './components/Register/RegisterUser';
import DrawingBoard from './components/Draw/DrawingBoard';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/create" element={<CreateRoom />} />
        <Route path="/rooms" element={<RoomList />} />
        <Route path="/register" element={<RegisterUser />} />
        <Route path="/draw" element={<DrawingBoard />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;