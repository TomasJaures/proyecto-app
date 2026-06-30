import { describe, it, expect, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext.jsx";
import Navbar from "../../components/Navbar.jsx";

const mockLogout = vi.fn();

function renderNavbar(props = {}) {
  const defaultProps = { role: "Alumno", name: "Juan" };
  return render(
    <AuthContext.Provider value={{ user: null, login: vi.fn(), logout: mockLogout, isAuthenticated: false }}>
      <MemoryRouter>
        <Navbar {...defaultProps} {...props} />
      </MemoryRouter>
    </AuthContext.Provider>
  );
}

describe("Navbar", () => {
  it("renders the RUA logo", () => {
    renderNavbar();
    expect(screen.getByText("RUA")).toBeInTheDocument();
  });

  it("displays role and name", () => {
    renderNavbar({ role: "Docente", name: "María" });
    expect(screen.getByText("Docente")).toBeInTheDocument();
    expect(screen.getByText("María")).toBeInTheDocument();
  });

  it("calls logout when Salir is clicked", () => {
    renderNavbar();
    fireEvent.click(screen.getByText("Salir"));
    expect(mockLogout).toHaveBeenCalledOnce();
  });
});
