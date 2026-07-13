import { describe, it, expect } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import FatalError from "../../pages/FatalError.jsx";

const mockedNavigate = vi.fn();
vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return { ...actual, useNavigate: () => mockedNavigate };
});

describe("FatalError", () => {
  it("renders the error page content", () => {
    render(<MemoryRouter><FatalError /></MemoryRouter>);
    expect(screen.getByText("Ha ocurrido un error")).toBeInTheDocument();
    expect(screen.getByText("Algo salió mal. Inténtalo nuevamente.")).toBeInTheDocument();
  });

  it("navigates to login on button click", () => {
    render(<MemoryRouter><FatalError /></MemoryRouter>);
    fireEvent.click(screen.getByText("Volver al Log In"));
    expect(mockedNavigate).toHaveBeenCalledWith("/login");
  });
});
