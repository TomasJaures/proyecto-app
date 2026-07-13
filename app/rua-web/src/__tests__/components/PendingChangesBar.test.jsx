import { describe, it, expect, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import PendingChangesBar from "../../components/PendingChangesBar.jsx";

describe("PendingChangesBar", () => {
  const defaultProps = {
    message: "Tienes cambios sin guardar",
    onSave: vi.fn(),
    onCancel: vi.fn(),
    isSaving: false,
  };

  it("displays the pending message", () => {
    render(<PendingChangesBar {...defaultProps} />);
    expect(screen.getByText("Tienes cambios sin guardar")).toBeInTheDocument();
  });

  it("calls onSave when Guardar is clicked", () => {
    const onSave = vi.fn();
    render(<PendingChangesBar {...defaultProps} onSave={onSave} />);
    fireEvent.click(screen.getByText("Guardar"));
    expect(onSave).toHaveBeenCalledOnce();
  });

  it("calls onCancel when Cancelar is clicked", () => {
    const onCancel = vi.fn();
    render(<PendingChangesBar {...defaultProps} onCancel={onCancel} />);
    fireEvent.click(screen.getByText("Cancelar"));
    expect(onCancel).toHaveBeenCalledOnce();
  });

  it("disables buttons while saving", () => {
    render(<PendingChangesBar {...defaultProps} isSaving={true} />);
    expect(screen.getByText("Guardando...")).toBeDisabled();
    expect(screen.getByText("Cancelar")).toBeDisabled();
  });

  it("applies danger variant class", () => {
    const { container } = render(
      <PendingChangesBar {...defaultProps} variant="danger" />
    );
    expect(container.firstChild).toHaveClass("barra-pendiente-peligro");
  });
});
