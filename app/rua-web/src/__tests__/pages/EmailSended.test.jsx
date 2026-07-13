import { describe, it, expect } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import EmailSended from "../../pages/EmailSended.jsx";

const mockedNavigate = vi.fn();
vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return { ...actual, useNavigate: () => mockedNavigate };
});

describe("EmailSended", () => {
  it("renders the confirmation content", () => {
    render(<MemoryRouter><EmailSended /></MemoryRouter>);
    expect(screen.getByText("¡Te hemos enviado un correo!")).toBeInTheDocument();
    expect(screen.getByText("Revisa tu bandeja de entrada para continuar.")).toBeInTheDocument();
  });

  it("navigates to login on button click", () => {
    render(<MemoryRouter><EmailSended /></MemoryRouter>);
    fireEvent.click(screen.getByText("Volver al Log In"));
    expect(mockedNavigate).toHaveBeenCalledWith("/login");
  });
});
