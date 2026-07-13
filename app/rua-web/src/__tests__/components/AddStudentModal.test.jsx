import { describe, it, expect, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import AddStudentModal from "../../components/AddStudentModal.jsx";

describe("AddStudentModal", () => {
  const defaultProps = {
    open: true,
    onClose: vi.fn(),
    onAdd: vi.fn(),
  };

  it("renders nothing when closed", () => {
    const { container } = render(
      <AddStudentModal open={false} onClose={vi.fn()} onAdd={vi.fn()} />
    );
    expect(container.innerHTML).toBe("");
  });

  it("renders form when open", () => {
    render(<AddStudentModal {...defaultProps} />);
    expect(screen.getByText("Añadir alumno")).toBeInTheDocument();
    expect(screen.getByPlaceholderText("alumno@ufromail.cl")).toBeInTheDocument();
  });

  it("shows error for empty email", () => {
    render(<AddStudentModal {...defaultProps} />);
    fireEvent.click(screen.getByText("Confirmar"));
    expect(screen.getByText("Ingresa un correo")).toBeInTheDocument();
  });

  it("shows error for invalid email", () => {
    render(<AddStudentModal {...defaultProps} />);
    const input = screen.getByPlaceholderText("alumno@ufromail.cl");
    fireEvent.change(input, { target: { value: "invalidemail" } });
    fireEvent.click(screen.getByText("Confirmar"));
    expect(screen.getByText("Correo inválido")).toBeInTheDocument();
  });

  it("calls onAdd with valid email", () => {
    const onAdd = vi.fn();
    render(<AddStudentModal {...defaultProps} onAdd={onAdd} />);
    const input = screen.getByPlaceholderText("alumno@ufromail.cl");
    fireEvent.change(input, { target: { value: "test@ufromail.cl" } });
    fireEvent.click(screen.getByText("Confirmar"));
    expect(onAdd).toHaveBeenCalledWith("test@ufromail.cl");
  });
});
